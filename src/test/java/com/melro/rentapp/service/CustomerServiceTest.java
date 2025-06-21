package com.melro.rentapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.melro.rentapp.dto.CreateUserDto;
import com.melro.rentapp.model.CustomerModel;
import com.melro.rentapp.repository.CustomerRepository;

/**
 * Unit tests for CustomerService
 *
 * These tests verify:
 * - Customer creation
 * - Customer search by ID and email
 * - Listing all customers
 * - Input data validation
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    private CustomerModel testCustomer;
    private CreateUserDto createUserDto;

    @BeforeEach
    void setUp() {
        // Setup test data
        testCustomer = new CustomerModel();
        testCustomer.setName("John Silva");
        testCustomer.setEmail("john@email.com");
        testCustomer.setPassword("encodedPassword123");

        createUserDto = new CreateUserDto("John Silva", "john@email.com", "password123");
    }

    @Test
    @DisplayName("Should create customer successfully")
    void shouldCreateCustomerSuccessfully() throws Exception {
        // Arrange
        CustomerModel savedCustomer = new CustomerModel();
        savedCustomer.setName("John Silva");
        savedCustomer.setEmail("john@email.com");
        savedCustomer.setPassword("encodedPassword123");

        // Use reflection to set the userId
        java.lang.reflect.Field userIdField = CustomerModel.class.getSuperclass().getDeclaredField("userId");
        userIdField.setAccessible(true);
        userIdField.set(savedCustomer, 1L);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword123");
        when(customerRepository.save(any(CustomerModel.class))).thenReturn(savedCustomer);

        // Act
        Long customerId = customerService.createCustomer(createUserDto);

        // Assert
        assertThat(customerId).isEqualTo(1L);
        verify(passwordEncoder).encode("password123");
        verify(customerRepository).save(any(CustomerModel.class));
    }

    @Test
    @DisplayName("Should find customer by ID when exists")
    void shouldFindCustomerByIdWhenExists() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        // Act
        Optional<CustomerModel> result = customerService.findById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("John Silva");
        verify(customerRepository).findById(1L);
    }

    @Test
    @DisplayName("Should return empty Optional when customer not found")
    void shouldReturnEmptyOptionalWhenCustomerNotFound() {
        // Arrange
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<CustomerModel> result = customerService.findById(999L);

        // Assert
        assertThat(result).isEmpty();
        verify(customerRepository).findById(999L);
    }

    @Test
    @DisplayName("Should find customer by email when exists")
    void shouldFindCustomerByEmailWhenExists() {
        // Arrange
        when(customerRepository.findByEmail("john@email.com")).thenReturn(Optional.of(testCustomer));

        // Act
        Optional<CustomerModel> result = customerService.findByEmail("john@email.com");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("john@email.com");
        verify(customerRepository).findByEmail("john@email.com");
    }

    @Test
    @DisplayName("Should return empty Optional when email not found")
    void shouldReturnEmptyOptionalWhenEmailNotFound() {
        // Arrange
        when(customerRepository.findByEmail("nonexistent@email.com")).thenReturn(Optional.empty());

        // Act
        Optional<CustomerModel> result = customerService.findByEmail("nonexistent@email.com");

        // Assert
        assertThat(result).isEmpty();
        verify(customerRepository).findByEmail("nonexistent@email.com");
    }

    @Test
    @DisplayName("Should return list of all customers")
    void shouldReturnAllCustomers() {
        // Arrange
        CustomerModel customer2 = new CustomerModel();
        customer2.setName("Mary Santos");
        customer2.setEmail("mary@email.com");

        List<CustomerModel> customers = Arrays.asList(testCustomer, customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        List<CustomerModel> result = customerService.findAll();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).contains(testCustomer, customer2);
        verify(customerRepository).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no customers exist")
    void shouldReturnEmptyListWhenNoCustomers() {
        // Arrange
        when(customerRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<CustomerModel> result = customerService.findAll();

        // Assert
        assertThat(result).isEmpty();
        verify(customerRepository).findAll();
    }
}
