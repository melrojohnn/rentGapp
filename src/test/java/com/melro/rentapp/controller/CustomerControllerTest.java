package com.melro.rentapp.controller;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melro.rentapp.config.TestSecurityConfig;
import com.melro.rentapp.dto.CreateUserDto;
import com.melro.rentapp.model.CustomerModel;
import com.melro.rentapp.security.CustomUserDetailsService;
import com.melro.rentapp.security.JwtService;
import com.melro.rentapp.service.CustomerService;

/**
 * Integration tests for CustomerController
 *
 * These tests verify:
 * - HTTP endpoints behavior
 * - Request/response mapping
 * - Security configuration
 * - Error handling
 */
@WebMvcTest(CustomerController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerModel testCustomer;
    private CreateUserDto createUserDto;

    @BeforeEach
    void setUp() {
        testCustomer = new CustomerModel();
        testCustomer.setName("John Silva");
        testCustomer.setEmail("john@email.com");

        createUserDto = new CreateUserDto("John Silva", "john@email.com", "password123");
    }

    @Test
    @DisplayName("Should create customer successfully via POST")
    void shouldCreateCustomerSuccessfully() throws Exception {
        // Arrange
        when(customerService.createCustomer(any(CreateUserDto.class))).thenReturn(1L);

        // Act & Assert
        mockMvc.perform(post("/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should return customer by ID when exists")
    void shouldReturnCustomerByIdWhenExists() throws Exception {
        // Arrange
        when(customerService.findById(1L)).thenReturn(Optional.of(testCustomer));

        // Act & Assert
        mockMvc.perform(get("/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Silva"))
                .andExpect(jsonPath("$.email").value("john@email.com"));
    }

    @Test
    @DisplayName("Should return 404 when customer not found")
    void shouldReturn404WhenCustomerNotFound() throws Exception {
        // Arrange
        when(customerService.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/v1/customers/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return list of all customers")
    void shouldReturnAllCustomers() throws Exception {
        // Arrange
        CustomerModel customer2 = new CustomerModel();
        customer2.setName("Mary Santos");
        customer2.setEmail("mary@email.com");

        when(customerService.findAll()).thenReturn(Arrays.asList(testCustomer, customer2));

        // Act & Assert
        mockMvc.perform(get("/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("John Silva"))
                .andExpect(jsonPath("$[1].name").value("Mary Santos"));
    }

    @Test
    @DisplayName("Should return empty list when no customers exist")
    void shouldReturnEmptyListWhenNoCustomers() throws Exception {
        // Arrange
        when(customerService.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Should return 200 when not authenticated (test config allows all)")
    void shouldReturn200WhenNotAuthenticated() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/v1/customers"))
                .andExpect(status().isOk());
    }
}
