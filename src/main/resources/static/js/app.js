// Configuração da API
const API_BASE_URL = 'http://localhost:8099/v1';
const AUTH_HEADER = 'Basic YWRtaW46YWRtaW4=';

// Estado global da aplicação
let currentSection = 'dashboard';
let selectedEquipment = [];

// Inicialização da aplicação
document.addEventListener('DOMContentLoaded', function() {
    loadDashboard();
    setupEventListeners();
});

// Configuração de event listeners
function setupEventListeners() {
    // Equipment type change
    document.getElementById('equipmentType')?.addEventListener('change', loadEquipmentOptions);

    // Plan change in order modal
    document.getElementById('orderPlan')?.addEventListener('change', updateEquipmentLimits);
}

// Navegação entre seções
function showSection(sectionName) {
    // Esconder todas as seções
    document.querySelectorAll('.content-section').forEach(section => {
        section.style.display = 'none';
    });

    // Remover classe active de todos os links
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
    });

    // Mostrar seção selecionada
    document.getElementById(sectionName + '-section').style.display = 'block';

    // Adicionar classe active ao link
    event.target.classList.add('active');

    // Carregar dados da seção
    switch(sectionName) {
        case 'dashboard':
            loadDashboard();
            break;
        case 'customers':
            loadCustomers();
            break;
        case 'plans':
            loadPlans();
            break;
        case 'equipment':
            loadEquipment();
            break;
        case 'orders':
            loadOrders();
            break;
    }

    currentSection = sectionName;
}

// ==================== DASHBOARD ====================
async function loadDashboard() {
    try {
        showLoading(true);

        // Carregar contadores
        const [customers, plans, laptops, tablets, smartphones, orders] = await Promise.all([
            fetchAPI('/customers'),
            fetchAPI('/plans'),
            fetchAPI('/laptops'),
            fetchAPI('/tablets'),
            fetchAPI('/smartphones'),
            fetchAPI('/order')
        ]);

        document.getElementById('total-customers').textContent = customers.length;
        document.getElementById('available-equipment').textContent = laptops.length + tablets.length + smartphones.length;
        document.getElementById('active-orders').textContent = orders.length;

        // Calcular receita total
        const revenue = orders.reduce((sum, order) => sum + (order.plan?.price || 0), 0);
        document.getElementById('total-revenue').textContent = '$' + revenue.toFixed(2);

        // Carregar pedidos recentes
        displayRecentOrders(orders.slice(0, 5));

    } catch (error) {
        showAlert('Erro ao carregar dashboard: ' + error.message, 'danger');
    } finally {
        showLoading(false);
    }
}

function displayRecentOrders(orders) {
    const container = document.getElementById('recent-orders');

    if (orders.length === 0) {
        container.innerHTML = '<p class="text-muted">Nenhum pedido encontrado.</p>';
        return;
    }

    const table = `
        <table class="table table-sm">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Cliente</th>
                    <th>Plano</th>
                    <th>Data</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                ${orders.map(order => `
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.customer.name}</td>
                        <td>${order.plan.name}</td>
                        <td>${new Date(order.orderDate).toLocaleDateString()}</td>
                        <td><span class="badge bg-success">Ativo</span></td>
                    </tr>
                `).join('')}
            </tbody>
        </table>
    `;

    container.innerHTML = table;
}

// ==================== CLIENTES ====================
async function loadCustomers() {
    try {
        showLoading(true);
        const customers = await fetchAPI('/customers');
        displayCustomers(customers);
    } catch (error) {
        showAlert('Erro ao carregar clientes: ' + error.message, 'danger');
    } finally {
        showLoading(false);
    }
}

function displayCustomers(customers) {
    const tbody = document.getElementById('customersTableBody');

    if (customers.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" class="text-center">Nenhum cliente encontrado.</td></tr>';
        return;
    }

    tbody.innerHTML = customers.map(customer => `
        <tr>
            <td>${customer.name}</td>
            <td>${customer.email}</td>
            <td>${customer.userId}</td>
            <td>
                <button class="btn btn-sm btn-outline-primary me-1" onclick="editCustomer(${customer.userId})">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger" onclick="deleteCustomer(${customer.userId})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        </tr>
    `).join('');
}

function showCustomerModal(customerId = null) {
    const modal = new bootstrap.Modal(document.getElementById('customerModal'));
    const title = document.getElementById('customerModalTitle');
    const form = document.getElementById('customerForm');

    if (customerId) {
        title.textContent = 'Editar Cliente';
        // TODO: Implementar edição
    } else {
        title.textContent = 'Novo Cliente';
        form.reset();
    }

    modal.show();
}

async function saveCustomer() {
    try {
        const formData = {
            name: document.getElementById('customerName').value,
            email: document.getElementById('customerEmail').value,
            password: document.getElementById('customerPassword').value
        };

        await fetchAPI('/customers', {
            method: 'POST',
            body: JSON.stringify(formData)
        });

        showAlert('Cliente criado com sucesso!', 'success');
        bootstrap.Modal.getInstance(document.getElementById('customerModal')).hide();
        loadCustomers();

    } catch (error) {
        showAlert('Erro ao criar cliente: ' + error.message, 'danger');
    }
}

async function deleteCustomer(customerId) {
    if (!confirm('Tem certeza que deseja excluir este cliente?')) return;

    try {
        await fetchAPI(`/customers/${customerId}`, { method: 'DELETE' });
        showAlert('Cliente excluído com sucesso!', 'success');
        loadCustomers();
    } catch (error) {
        showAlert('Erro ao excluir cliente: ' + error.message, 'danger');
    }
}

// ==================== PLANOS ====================
async function loadPlans() {
    try {
        showLoading(true);
        const plans = await fetchAPI('/plans');
        displayPlans(plans);
    } catch (error) {
        showAlert('Erro ao carregar planos: ' + error.message, 'danger');
    } finally {
        showLoading(false);
    }
}

function displayPlans(plans) {
    const tbody = document.getElementById('plansTableBody');

    if (plans.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="text-center">Nenhum plano encontrado.</td></tr>';
        return;
    }

    tbody.innerHTML = plans.map(plan => `
        <tr>
            <td>${plan.name}</td>
            <td>$${plan.price}</td>
            <td>${getDurationText(plan.duration)}</td>
            <td><span class="badge ${getPlanTypeColor(plan.planType)}">${getPlanTypeText(plan.planType)}</span></td>
            <td>${plan.description || '-'}</td>
        </tr>
    `).join('');
}

function getDurationText(duration) {
    const durations = {
        'THREE_MONTHS': '3 Meses',
        'SIX_MONTHS': '6 Meses',
        'TWELVE_MONTHS': '12 Meses'
    };
    return durations[duration] || duration;
}

function getPlanTypeText(type) {
    const types = {
        'BASIC': 'Básico',
        'STANDARD': 'Padrão',
        'PREMIUM': 'Premium'
    };
    return types[type] || type;
}

function getPlanTypeColor(type) {
    const colors = {
        'BASIC': 'secondary',
        'STANDARD': 'primary',
        'PREMIUM': 'success'
    };
    return colors[type] || 'secondary';
}

function showPlanModal(planId = null) {
    const modal = new bootstrap.Modal(document.getElementById('planModal'));
    const title = document.getElementById('planModalTitle');
    const form = document.getElementById('planForm');

    if (planId) {
        title.textContent = 'Editar Plano';
        // TODO: Implementar edição
    } else {
        title.textContent = 'Novo Plano';
        form.reset();
    }

    modal.show();
}

async function savePlan() {
    try {
        const formData = {
            name: document.getElementById('planName').value,
            price: parseFloat(document.getElementById('planPrice').value),
            duration: document.getElementById('planDuration').value,
            planType: document.getElementById('planType').value,
            description: document.getElementById('planDescription').value
        };

        await fetchAPI('/plans', {
            method: 'POST',
            body: JSON.stringify(formData)
        });

        showAlert('Plano criado com sucesso!', 'success');
        bootstrap.Modal.getInstance(document.getElementById('planModal')).hide();
        loadPlans();

    } catch (error) {
        showAlert('Erro ao criar plano: ' + error.message, 'danger');
    }
}

async function deletePlan(planId) {
    if (!confirm('Tem certeza que deseja excluir este plano?')) return;

    try {
        await fetchAPI(`/plans/${planId}`, { method: 'DELETE' });
        showAlert('Plano excluído com sucesso!', 'success');
        loadPlans();
    } catch (error) {
        showAlert('Erro ao excluir plano: ' + error.message, 'danger');
    }
}

// ==================== EQUIPAMENTOS ====================
async function loadEquipment() {
    try {
        showLoading(true);
        const [laptops, tablets, smartphones] = await Promise.all([
            fetchAPI('/laptops'),
            fetchAPI('/tablets'),
            fetchAPI('/smartphones')
        ]);

        displayAllEquipment(laptops, tablets, smartphones);

    } catch (error) {
        showAlert('Erro ao carregar equipamentos: ' + error.message, 'danger');
    } finally {
        showLoading(false);
    }
}

function displayAllEquipment(laptops, tablets, smartphones) {
    const tbody = document.getElementById('equipmentTableBody');

    const allEquipment = [
        ...laptops.map(laptop => ({
            ...laptop,
            type: 'Laptop',
            specifications: `CPU: ${laptop.cpu}, GPU: ${laptop.gpu}, RAM: ${laptop.ram}, HD: ${laptop.hd}`,
            id: laptop.laptopId
        })),
        ...tablets.map(tablet => ({
            ...tablet,
            type: 'Tablet',
            specifications: `Screen: ${tablet.screen}, Camera: ${tablet.camera}, HD: ${tablet.hd}`,
            id: tablet.id
        })),
        ...smartphones.map(smartphone => ({
            ...smartphone,
            type: 'Smartphone',
            specifications: `Screen: ${smartphone.screen}, Camera: ${smartphone.camera}, HD: ${smartphone.hd}`,
            id: smartphone.id
        }))
    ];

    if (allEquipment.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="text-center">Nenhum equipamento encontrado.</td></tr>';
        return;
    }

    tbody.innerHTML = allEquipment.map(equipment => `
        <tr>
            <td>${equipment.model}</td>
            <td><span class="badge bg-primary">${equipment.type}</span></td>
            <td>${equipment.brand}</td>
            <td><small>${equipment.specifications}</small></td>
            <td><span class="badge bg-success">Available</span></td>
        </tr>
    `).join('');
}

function showLaptopModal() {
    const modal = new bootstrap.Modal(document.getElementById('laptopModal'));
    document.getElementById('laptopForm').reset();
    modal.show();
}

async function saveLaptop() {
    try {
        const formData = {
            model: document.getElementById('laptopModel').value,
            brand: document.getElementById('laptopBrand').value,
            cpu: document.getElementById('laptopCpu').value,
            gpu: document.getElementById('laptopGpu').value,
            ram: document.getElementById('laptopRam').value,
            hd: document.getElementById('laptopHd').value
        };

        await fetchAPI('/laptops', {
            method: 'POST',
            body: JSON.stringify(formData)
        });

        showAlert('Laptop criado com sucesso!', 'success');
        bootstrap.Modal.getInstance(document.getElementById('laptopModal')).hide();
        loadEquipment();

    } catch (error) {
        showAlert('Erro ao criar laptop: ' + error.message, 'danger');
    }
}

// ==================== PEDIDOS ====================
async function loadOrders() {
    try {
        showLoading(true);
        const orders = await fetchAPI('/order');
        displayOrders(orders);
    } catch (error) {
        showAlert('Erro ao carregar pedidos: ' + error.message, 'danger');
    } finally {
        showLoading(false);
    }
}

function displayOrders(orders) {
    const tbody = document.getElementById('ordersTableBody');

    if (orders.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="text-center">Nenhum pedido encontrado.</td></tr>';
        return;
    }

    tbody.innerHTML = orders.map(order => {
        const equipmentCount = (order.laptops?.length || 0) + (order.tablets?.length || 0) + (order.smartphones?.length || 0);

        return `
            <tr>
                <td>${order.id}</td>
                <td>${order.customer.name}</td>
                <td>${order.plan.name}</td>
                <td>${new Date(order.orderDate).toLocaleDateString()}</td>
                <td>${new Date(order.orderEndDate).toLocaleDateString()}</td>
                <td>${equipmentCount} equipamento(s)</td>
                <td>
                    <button class="btn btn-sm btn-outline-info me-1" onclick="viewOrder(${order.id})">
                        <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="deleteOrder(${order.id})">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `;
    }).join('');
}

function showOrderModal() {
    const modal = new bootstrap.Modal(document.getElementById('orderModal'));
    document.getElementById('orderForm').reset();
    selectedEquipment = [];
    updateSelectedEquipmentDisplay();

    // Carregar clientes e planos
    loadCustomersForOrder();
    loadPlansForOrder();

    modal.show();
}

async function loadCustomersForOrder() {
    try {
        const customers = await fetchAPI('/customers');
        const select = document.getElementById('orderCustomer');
        select.innerHTML = '<option value="">Selecione um cliente...</option>' +
            customers.map(c => `<option value="${c.userId}">${c.name} (${c.email})</option>`).join('');
    } catch (error) {
        console.error('Erro ao carregar clientes:', error);
    }
}

async function loadPlansForOrder() {
    try {
        const plans = await fetchAPI('/plans');
        const select = document.getElementById('orderPlan');
        select.innerHTML = '<option value="">Selecione um plano...</option>' +
            plans.map(p => `<option value="${p.id}">${p.name} - ${getPlanTypeText(p.planType)}</option>`).join('');
    } catch (error) {
        console.error('Erro ao carregar planos:', error);
    }
}

async function loadEquipmentOptions() {
    const equipmentType = document.getElementById('equipmentType').value;
    const select = document.getElementById('equipmentId');

    try {
        let equipment = [];
        switch (equipmentType) {
            case 'LAPTOP':
                equipment = await fetchAPI('/laptops');
                break;
            case 'TABLET':
                equipment = await fetchAPI('/tablets');
                break;
            case 'SMARTPHONE':
                equipment = await fetchAPI('/smartphones');
                break;
        }

        select.innerHTML = '<option value="">Selecione...</option>' +
            equipment.map(e => {
                const id = e.laptopId || e.id; // laptopId para laptops, id para tablets/smartphones
                return `<option value="${id}">${e.model} - ${e.brand}</option>`;
            }).join('');

    } catch (error) {
        console.error('Erro ao carregar equipamentos:', error);
    }
}

function addEquipment() {
    const type = document.getElementById('equipmentType').value;
    const id = document.getElementById('equipmentId').value;
    const select = document.getElementById('equipmentId');
    const option = select.options[select.selectedIndex];

    if (!id) {
        showAlert('Selecione um equipamento', 'warning');
        return;
    }

    // Verificar se já foi adicionado
    if (selectedEquipment.some(e => e.equipmentId === parseInt(id) && e.equipmentType === type)) {
        showAlert('Este equipamento já foi adicionado', 'warning');
        return;
    }

    selectedEquipment.push({
        equipmentType: type,
        equipmentId: parseInt(id),
        displayName: option.text
    });

    updateSelectedEquipmentDisplay();
    document.getElementById('equipmentId').value = '';
}

function removeEquipment(index) {
    selectedEquipment.splice(index, 1);
    updateSelectedEquipmentDisplay();
}

function updateSelectedEquipmentDisplay() {
    const container = document.getElementById('selected-equipment');

    if (selectedEquipment.length === 0) {
        container.innerHTML = '<p class="text-muted">Nenhum equipamento selecionado</p>';
        return;
    }

    container.innerHTML = selectedEquipment.map((equipment, index) => `
        <div class="equipment-item d-flex justify-content-between align-items-center">
            <span>${equipment.displayName}</span>
            <button type="button" class="btn btn-sm btn-outline-danger remove-btn" onclick="removeEquipment(${index})">
                <i class="fas fa-times"></i>
            </button>
        </div>
    `).join('');
}

function updateEquipmentLimits() {
    const planId = document.getElementById('orderPlan').value;
    if (!planId) return;

    // TODO: Implementar validação de limites baseada no plano
    // Por enquanto, apenas recarregar as opções de equipamento
    loadEquipmentOptions();
}

async function saveOrder() {
    try {
        const customerId = document.getElementById('orderCustomer').value;
        const planId = document.getElementById('orderPlan').value;

        if (!customerId || !planId || selectedEquipment.length === 0) {
            showAlert('Preencha todos os campos obrigatórios', 'warning');
            return;
        }

        const orderData = {
            customerId: parseInt(customerId),
            planId: parseInt(planId),
            equipmentItems: selectedEquipment
        };

        await fetchAPI('/order', {
            method: 'POST',
            body: JSON.stringify(orderData)
        });

        showAlert('Pedido criado com sucesso!', 'success');
        bootstrap.Modal.getInstance(document.getElementById('orderModal')).hide();
        loadOrders();

    } catch (error) {
        showAlert('Erro ao criar pedido: ' + error.message, 'danger');
    }
}

async function deleteOrder(orderId) {
    if (!confirm('Tem certeza que deseja excluir este pedido?')) return;

    try {
        await fetchAPI(`/order/${orderId}`, { method: 'DELETE' });
        showAlert('Pedido excluído com sucesso!', 'success');
        loadOrders();
    } catch (error) {
        showAlert('Erro ao excluir pedido: ' + error.message, 'danger');
    }
}

// ==================== UTILITÁRIOS ====================
async function fetchAPI(endpoint, options = {}) {
    const url = API_BASE_URL + endpoint;
    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': AUTH_HEADER
        },
        ...options
    };

    if (options.body) {
        config.body = options.body;
    }

    const response = await fetch(url, config);

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`HTTP ${response.status}: ${errorText}`);
    }

    return response.json();
}

function showLoading(show) {
    const loading = document.getElementById('loading');
    if (show) {
        loading.classList.add('show');
    } else {
        loading.classList.remove('show');
    }
}

function showAlert(message, type = 'info') {
    const container = document.getElementById('alert-container');
    const alertId = 'alert-' + Date.now();

    const alert = `
        <div id="${alertId}" class="alert alert-${type} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;

    container.insertAdjacentHTML('beforeend', alert);

    // Auto-remove after 5 seconds
    setTimeout(() => {
        const alertElement = document.getElementById(alertId);
        if (alertElement) {
            alertElement.remove();
        }
    }, 5000);
}

// Funções placeholder para funcionalidades futuras
function editCustomer(id) {
    showAlert('Funcionalidade de edição será implementada em breve', 'info');
}

function editPlan(id) {
    showAlert('Funcionalidade de edição será implementada em breve', 'info');
}

function editLaptop(id) {
    showAlert('Funcionalidade de edição será implementada em breve', 'info');
}

function editTablet(id) {
    showAlert('Funcionalidade de edição será implementada em breve', 'info');
}

function editSmartphone(id) {
    showAlert('Funcionalidade de edição será implementada em breve', 'info');
}

function viewOrder(id) {
    showAlert('Funcionalidade de visualização será implementada em breve', 'info');
}

function deleteLaptop(id) {
    if (!confirm('Tem certeza que deseja excluir este laptop?')) return;
    showAlert('Funcionalidade de exclusão será implementada em breve', 'info');
}

function deleteTablet(id) {
    if (!confirm('Tem certeza que deseja excluir este tablet?')) return;
    showAlert('Funcionalidade de exclusão será implementada em breve', 'info');
}

function deleteSmartphone(id) {
    if (!confirm('Tem certeza que deseja excluir este smartphone?')) return;
    showAlert('Funcionalidade de exclusão será implementada em breve', 'info');
}

function showTabletModal() {
    showAlert('Modal de tablet será implementado em breve', 'info');
}

function showSmartphoneModal() {
    showAlert('Modal de smartphone será implementado em breve', 'info');
}
