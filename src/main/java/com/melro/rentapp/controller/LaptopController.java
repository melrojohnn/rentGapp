package com.melro.rentapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melro.rentapp.dto.CreateLaptopDTO;
import com.melro.rentapp.model.LaptopModel;
import com.melro.rentapp.service.LaptopService;

/**
 * REST Controller for managing laptop equipment through HTTP endpoints.
 *
 * This controller provides a RESTful API for creating, retrieving, and managing
 * laptop equipment in the rental system.
 *
 * API Endpoints:
 * - POST /v1/laptops - Create a new laptop
 * - GET /v1/laptops - Retrieve all laptops
 * - GET /v1/laptops/{id} - Retrieve a specific laptop by ID
 *
 * All endpoints return appropriate HTTP status codes and follow REST
 * conventions.
 */
@RestController
@RequestMapping("/v1/laptops")
public class LaptopController {

    /**
     * Service layer for laptop business logic.
     * Injected via constructor for dependency injection.
     */
    private final LaptopService laptopService;

    /**
     * Constructor for dependency injection of LaptopService.
     *
     * @param laptopService Service responsible for laptop business logic
     */
    @Autowired
    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    /**
     * Creates a new laptop equipment.
     *
     * Accepts a CreateLaptopDTO containing laptop specifications.
     * Returns a 201 Created status with the location of the new laptop.
     *
     * @param createLaptopDTO Contains the laptop specifications
     * @return ResponseEntity with 201 status and location header, or error status
     */
    @PostMapping
    public ResponseEntity<LaptopModel> createLaptop(@RequestBody CreateLaptopDTO createLaptopDTO) {
        var id = laptopService.createLaptop(createLaptopDTO);
        var laptop = laptopService.findById(id).orElseThrow();
        return ResponseEntity.created(URI.create("/v1/laptops/" + id)).body(laptop);
    }

    /**
     * Retrieves all laptops in the system.
     *
     * Returns a list of all available laptops with 200 OK status.
     *
     * @return ResponseEntity containing a list of all laptops
     */
    @GetMapping
    public ResponseEntity<List<LaptopModel>> getAllLaptops() {
        return ResponseEntity.ok(laptopService.findAll());
    }

    /**
     * Retrieves a specific laptop by its ID.
     *
     * Returns the laptop if found, or 404 Not Found if the laptop doesn't exist.
     *
     * @param id The unique identifier of the laptop to retrieve
     * @return ResponseEntity with the laptop data (200 OK) or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<LaptopModel> getLaptopById(@PathVariable Long id) {
        return laptopService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
