package com.melro.rentapp.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.melro.rentapp.dto.OrderRequestDTO;
import com.melro.rentapp.model.CustomerModel;
import com.melro.rentapp.model.LaptopModel;
import com.melro.rentapp.model.OrderModel;
import com.melro.rentapp.model.PlansModel;
import com.melro.rentapp.model.SmartphoneModel;
import com.melro.rentapp.model.TabletModel;
import com.melro.rentapp.repository.CustomerRepository;
import com.melro.rentapp.repository.LaptopRepository;
import com.melro.rentapp.repository.OrderRepository;
import com.melro.rentapp.repository.PlansRepository;
import com.melro.rentapp.repository.SmartphoneRepository;
import com.melro.rentapp.repository.TabletRepository;

/**
 * Service class responsible for managing rental orders in the system.
 *
 * This service handles the business logic for creating, retrieving, and
 * managing orders.
 * It coordinates between different entities (customers, plans, equipment) to
 * create
 * complete rental orders with proper validation and relationships.
 *
 * Business Rules:
 * - Orders can only be created with valid customer, plan, and equipment IDs
 * - All referenced entities must exist in the database
 * - Equipment count and types must match plan restrictions
 * - Order dates are automatically managed by the OrderModel entity
 * - End dates are calculated based on plan duration automatically
 * - Orders can contain multiple pieces of equipment (laptops, tablets,
 * smartphones)
 */
@Service
public class OrderService {

    /**
     * Repository for database operations on orders.
     */
    private final OrderRepository orderRepository;

    /**
     * Repository for customer data access.
     */
    private final CustomerRepository customerRepository;

    /**
     * Repository for rental plan data access.
     */
    private final PlansRepository plansRepository;

    /**
     * Repository for laptop equipment data access.
     */
    private final LaptopRepository laptopRepository;

    /**
     * Repository for tablet equipment data access.
     */
    private final TabletRepository tabletRepository;

    /**
     * Repository for smartphone equipment data access.
     */
    private final SmartphoneRepository smartphoneRepository;

    /**
     * Service for validating order creation against plan rules.
     */
    private final OrderValidationService orderValidationService;

    /**
     * Constructor for dependency injection of required repositories and services.
     *
     * @param orderRepository        Repository for order operations
     * @param customerRepository     Repository for customer operations
     * @param plansRepository        Repository for plan operations
     * @param laptopRepository       Repository for laptop equipment operations
     * @param tabletRepository       Repository for tablet equipment operations
     * @param smartphoneRepository   Repository for smartphone equipment operations
     * @param orderValidationService Service for order validation
     */
    public OrderService(OrderRepository orderRepository,
            CustomerRepository customerRepository,
            PlansRepository plansRepository,
            LaptopRepository laptopRepository,
            TabletRepository tabletRepository,
            SmartphoneRepository smartphoneRepository,
            OrderValidationService orderValidationService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.plansRepository = plansRepository;
        this.laptopRepository = laptopRepository;
        this.tabletRepository = tabletRepository;
        this.smartphoneRepository = smartphoneRepository;
        this.orderValidationService = orderValidationService;
    }

    /**
     * Creates a new rental order with the specified customer, plan, and equipment.
     *
     * This method validates that all referenced entities exist and creates a
     * complete
     * order with proper relationships. The order date and end date are
     * automatically
     * managed by the OrderModel entity.
     *
     * Business Logic:
     * - Validates customer exists in the system
     * - Validates plan exists and is available
     * - Validates equipment count and types against plan rules
     * - Loads all requested equipment by their IDs and types
     * - Creates order with proper entity relationships
     * - Saves the order to the database
     * - Returns the generated order ID
     *
     * @param orderRequestDTO Contains customer ID, plan ID, and list of equipment
     *                        items
     * @return The ID of the newly created order
     * @throws RuntimeException         if customer, plan, or any equipment is not
     *                                  found
     * @throws IllegalArgumentException if order validation fails
     */
    public Long createOrder(OrderRequestDTO orderRequestDTO) {
        // Load customer by ID - validates customer exists
        CustomerModel customer = customerRepository.findById(orderRequestDTO.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Load plan by ID - validates plan exists
        PlansModel plan = plansRepository.findById(orderRequestDTO.planId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        // Validate order against plan rules
        orderValidationService.validateOrder(orderRequestDTO, plan);

        // Load equipment based on type and ID
        List<LaptopModel> laptops = new ArrayList<>();
        List<TabletModel> tablets = new ArrayList<>();
        List<SmartphoneModel> smartphones = new ArrayList<>();

        loadEquipmentByType(orderRequestDTO, laptops, tablets, smartphones);

        // Create order and set required relationships
        OrderModel order = new OrderModel();
        order.setCustomer(customer);
        order.setPlan(plan);
        order.setLaptops(laptops);
        order.setTablets(tablets);
        order.setSmartphones(smartphones);

        // Calcular manualmente a data de término do pedido
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = switch (plan.getDuration()) {
            case THREE_MONTHS -> startDate.plusMonths(3);
            case SIX_MONTHS -> startDate.plusMonths(6);
            case TWELVE_MONTHS -> startDate.plusMonths(12);
        };
        order.setOrderEndDate(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Save to database - triggers automatic date calculation via @PrePersist
        OrderModel saved = orderRepository.save(order);
        return saved.getId();
    }

    /**
     * Loads equipment from the database based on the equipment items in the
     * request.
     * Supports laptops, tablets, and smartphones.
     *
     * @param orderRequestDTO The order request containing equipment items
     * @param laptops         List to populate with found laptops
     * @param tablets         List to populate with found tablets
     * @param smartphones     List to populate with found smartphones
     * @throws RuntimeException if any equipment is not found
     */
    private void loadEquipmentByType(OrderRequestDTO orderRequestDTO,
            List<LaptopModel> laptops,
            List<TabletModel> tablets,
            List<SmartphoneModel> smartphones) {
        // Process each equipment item by type
        for (OrderRequestDTO.EquipmentItemDTO item : orderRequestDTO.equipmentItems()) {
            switch (item.equipmentType()) {
                case LAPTOP:
                    LaptopModel laptop = laptopRepository.findById(item.equipmentId())
                            .orElseThrow(
                                    () -> new RuntimeException("Laptop with ID " + item.equipmentId() + " not found"));
                    laptops.add(laptop);
                    break;
                case TABLET:
                    TabletModel tablet = tabletRepository.findById(item.equipmentId())
                            .orElseThrow(
                                    () -> new RuntimeException("Tablet with ID " + item.equipmentId() + " not found"));
                    tablets.add(tablet);
                    break;
                case SMARTPHONE:
                    SmartphoneModel smartphone = smartphoneRepository.findById(item.equipmentId())
                            .orElseThrow(() -> new RuntimeException(
                                    "Smartphone with ID " + item.equipmentId() + " not found"));
                    smartphones.add(smartphone);
                    break;
                default:
                    throw new RuntimeException("Unknown equipment type: " + item.equipmentType());
            }
        }
    }

    /**
     * Retrieves all orders from the database.
     *
     * @return List of all orders in the system
     */
    public List<OrderModel> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Finds an order by its unique identifier.
     *
     * @param id The order ID to search for
     * @return Optional containing the order if found, empty otherwise
     */
    public java.util.Optional<OrderModel> findById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Deletes an order by its unique identifier.
     *
     * @param id The order ID to delete
     */
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
