package com.melro.rentapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melro.rentapp.dto.OrderRequestDTO;
import com.melro.rentapp.model.OrderModel;
import com.melro.rentapp.service.OrderService;

/**
 * REST Controller for managing rental orders through HTTP endpoints.
 *
 * This controller provides a RESTful API for creating, retrieving, and managing
 * rental orders. It handles HTTP requests and delegates business logic to the
 * OrderService.
 *
 * API Endpoints:
 * - POST /v1/order - Create a new rental order
 * - GET /v1/order/{id} - Retrieve a specific order by ID
 * - GET /v1/order - Retrieve all orders
 * - DELETE /v1/order/{id} - Delete an order by ID
 *
 * All endpoints return appropriate HTTP status codes and follow REST
 * conventions.
 */
@RestController
@RequestMapping("/v1/order")
public class OrderController {

    /**
     * Service layer for order business logic.
     * Injected via constructor for dependency injection.
     */
    private final OrderService orderService;

    /**
     * Constructor for dependency injection of OrderService.
     *
     * @param orderService Service responsible for order business logic
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Creates a new rental order.
     *
     * Accepts an OrderRequestDTO containing customer ID, plan ID, and equipment
     * IDs.
     * Returns a 201 Created status with the location of the new order.
     *
     * @param createOrderRequestDTO Contains the order details (customer, plan,
     *                              equipment)
     * @return ResponseEntity with 201 status and location header, or error status
     */
    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequestDTO createOrderRequestDTO) {
        Long id = orderService.createOrder(createOrderRequestDTO);
        return ResponseEntity.created(URI.create("/v1/order" + id)).build();
    }

    /**
     * Retrieves a specific order by its ID.
     *
     * Returns the order if found, or 404 Not Found if the order doesn't exist.
     *
     * @param id The unique identifier of the order to retrieve
     * @return ResponseEntity with the order data (200 OK) or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> getPlanById(@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all orders in the system.
     *
     * Returns a list of all orders with 200 OK status.
     *
     * @return ResponseEntity containing a list of all orders
     */
    @GetMapping
    public ResponseEntity<List<OrderModel>> getAlloders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    /**
     * Deletes an order by its ID.
     *
     * Removes the order from the system and returns 204 No Content on success.
     * If the order doesn't exist, the operation is still considered successful.
     *
     * @param id The unique identifier of the order to delete
     * @return ResponseEntity with 204 No Content status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
