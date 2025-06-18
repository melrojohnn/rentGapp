package com.melro.rentapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.melro.rentapp.dto.CreateLaptopDTO;
import com.melro.rentapp.model.LaptopModel;
import com.melro.rentapp.repository.LaptopRepository;

/**
 * Service class responsible for managing laptop equipment in the system.
 *
 * This service handles the business logic for creating, retrieving, and
 * managing
 * laptop equipment. It coordinates with the repository layer for database
 * operations.
 *
 * Business Rules:
 * - Laptops must have valid specifications (model, brand, CPU, RAM, HD)
 * - GPU is optional and can be null
 * - Each laptop gets a unique identifier when created
 */
@Service
public class LaptopService {

    /**
     * Repository for database operations on laptops.
     */
    private final LaptopRepository laptopRepository;

    /**
     * Constructor for dependency injection of LaptopRepository.
     *
     * @param laptopRepository Repository for laptop operations
     */
    public LaptopService(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    /**
     * Creates a new laptop equipment.
     *
     * Converts the DTO to entity and saves it to the database.
     * Returns the generated laptop ID.
     *
     * @param dto Contains the laptop specifications
     * @return The ID of the newly created laptop
     */
    public Long createLaptop(CreateLaptopDTO dto) {
        LaptopModel entity;
        entity = new LaptopModel(
                dto.model(),
                dto.brand(),
                dto.gpu(),
                dto.cpu(),
                dto.ram(),
                dto.hd());

        var saved = laptopRepository.save(entity);
        return saved.getLaptopId();
    }

    /**
     * Retrieves all laptops from the database.
     *
     * @return List of all laptops in the system
     */
    public List<LaptopModel> findAll() {
        return laptopRepository.findAll();
    }

    /**
     * Finds a laptop by its unique identifier.
     *
     * @param laptopId The laptop ID to search for
     * @return Optional containing the laptop if found, empty otherwise
     */
    public Optional<LaptopModel> findById(Long laptopId) {
        return laptopRepository.findById(laptopId);
    }
}
