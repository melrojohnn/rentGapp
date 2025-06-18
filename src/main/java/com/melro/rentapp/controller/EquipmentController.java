package com.melro.rentapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melro.rentapp.dto.EquipmentDTO;
import com.melro.rentapp.service.EquipmentService;

/**
 * Equipment Controller
 *
 * This controller provides a consolidated view of all equipment types
 * (laptops, tablets, smartphones) for the frontend application.
 *
 * Best Practices:
 * - Uses a dedicated service layer (EquipmentService) instead of directly
 * accessing multiple services
 * - Returns DTOs instead of domain entities
 * - Follows single responsibility principle for the controller
 */
@RestController
@RequestMapping("/v1/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    /**
     * Get all equipment (laptops, tablets, phones) with type information
     *
     * @return List of all equipment with type and availability information
     */
    @GetMapping
    public ResponseEntity<List<EquipmentDTO>> getAllEquipment() {
        List<EquipmentDTO> allEquipment = equipmentService.getAllEquipment();
        return ResponseEntity.ok(allEquipment);
    }
}
