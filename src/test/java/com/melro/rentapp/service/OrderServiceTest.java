package com.melro.rentapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.melro.rentapp.dto.OrderRequestDTO;
import com.melro.rentapp.enums.EquipmentType;
import com.melro.rentapp.enums.PlanDuration;
import com.melro.rentapp.enums.PlanType;
import com.melro.rentapp.model.CustomerModel;
import com.melro.rentapp.model.LaptopModel;
import com.melro.rentapp.model.OrderModel;
import com.melro.rentapp.model.PlansModel;
import com.melro.rentapp.repository.CustomerRepository;
import com.melro.rentapp.repository.LaptopRepository;
import com.melro.rentapp.repository.OrderRepository;
import com.melro.rentapp.repository.PlansRepository;
import com.melro.rentapp.repository.SmartphoneRepository;
import com.melro.rentapp.repository.TabletRepository;

/**
 * Unit tests for OrderService
 *
 * These tests verify:
 * - Order creation with different equipment types
 * - Validation of existing entities (customer, plan, equipment)
 * - Error handling when entities don't exist
 * - Order search and listing
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PlansRepository plansRepository;

    @Mock
    private LaptopRepository laptopRepository;

    @Mock
    private TabletRepository tabletRepository;

    @Mock
    private SmartphoneRepository smartphoneRepository;

    @Mock
    private OrderValidationService orderValidationService;

    @InjectMocks
    private OrderService orderService;

    private CustomerModel testCustomer;
    private PlansModel testPlan;
    private LaptopModel testLaptop;
    private OrderModel testOrder;

    @BeforeEach
    void setUp() {
        // Setup test data
        testCustomer = new CustomerModel();
        testCustomer.setName("John Silva");
        testCustomer.setEmail("john@email.com");

        testPlan = new PlansModel();
        testPlan.setId(1L);
        testPlan.setName("Basic Plan");
        testPlan.setPlanType(PlanType.BASIC);
        testPlan.setDuration(PlanDuration.THREE_MONTHS);
        testPlan.setPrice(new java.math.BigDecimal("100.00"));

        testLaptop = new LaptopModel();
        testLaptop.setLaptopId(1L);
        testLaptop.setBrand("Dell");
        testLaptop.setModel("Inspiron 15");

        testOrder = new OrderModel();
        testOrder.setId(1L);
        testOrder.setCustomer(testCustomer);
        testOrder.setPlan(testPlan);
    }

    @Test
    @DisplayName("Should create order with laptop successfully")
    void shouldCreateOrderWithLaptopSuccessfully() {
        // Arrange
        OrderRequestDTO.EquipmentItemDTO laptopItem = new OrderRequestDTO.EquipmentItemDTO(EquipmentType.LAPTOP, 1L);
        OrderRequestDTO orderRequest = new OrderRequestDTO(1L, 1L, Arrays.asList(laptopItem));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(plansRepository.findById(1L)).thenReturn(Optional.of(testPlan));
        when(laptopRepository.findById(1L)).thenReturn(Optional.of(testLaptop));
        when(orderRepository.save(any(OrderModel.class))).thenReturn(testOrder);

        // Act
        Long orderId = orderService.createOrder(orderRequest);

        // Assert
        assertThat(orderId).isEqualTo(1L);
        verify(customerRepository).findById(1L);
        verify(plansRepository).findById(1L);
        verify(laptopRepository).findById(1L);
        verify(orderValidationService).validateOrder(orderRequest, testPlan);
        verify(orderRepository).save(any(OrderModel.class));
    }

    @Test
    @DisplayName("Should throw exception when customer not found")
    void shouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        OrderRequestDTO.EquipmentItemDTO laptopItem = new OrderRequestDTO.EquipmentItemDTO(EquipmentType.LAPTOP, 1L);
        OrderRequestDTO orderRequest = new OrderRequestDTO(999L, 1L, Arrays.asList(laptopItem));

        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> orderService.createOrder(orderRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Customer not found");

        verify(customerRepository).findById(999L);
        verifyNoInteractions(plansRepository, laptopRepository, orderRepository);
    }

    @Test
    @DisplayName("Should throw exception when plan not found")
    void shouldThrowExceptionWhenPlanNotFound() {
        // Arrange
        OrderRequestDTO.EquipmentItemDTO laptopItem = new OrderRequestDTO.EquipmentItemDTO(EquipmentType.LAPTOP, 1L);
        OrderRequestDTO orderRequest = new OrderRequestDTO(1L, 999L, Arrays.asList(laptopItem));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(plansRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> orderService.createOrder(orderRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Plan not found");

        verify(customerRepository).findById(1L);
        verify(plansRepository).findById(999L);
        verifyNoInteractions(laptopRepository, orderRepository);
    }

    @Test
    @DisplayName("Should return list of all orders")
    void shouldReturnAllOrders() {
        // Arrange
        OrderModel order2 = new OrderModel();
        order2.setId(2L);
        order2.setCustomer(testCustomer);
        order2.setPlan(testPlan);

        List<OrderModel> orders = Arrays.asList(testOrder, order2);
        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<OrderModel> result = orderService.findAll();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).contains(testOrder, order2);
        verify(orderRepository).findAll();
    }

    @Test
    @DisplayName("Should find order by ID when exists")
    void shouldFindOrderByIdWhenExists() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        // Act
        Optional<OrderModel> result = orderService.findById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(orderRepository).findById(1L);
    }

    @Test
    @DisplayName("Should return empty Optional when order not found")
    void shouldReturnEmptyOptionalWhenOrderNotFound() {
        // Arrange
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<OrderModel> result = orderService.findById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(orderRepository).findById(999L);
    }

    @Test
    @DisplayName("Should delete order by ID")
    void shouldDeleteOrderById() {
        // Arrange
        doNothing().when(orderRepository).deleteById(1L);

        // Act
        orderService.deleteById(1L);

        // Assert
        verify(orderRepository).deleteById(1L);
    }
}
