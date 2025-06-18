package com.melro.rentapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melro.rentapp.dto.EquipmentDTO;

/**
 * Equipment Service
 *
 * This service provides business logic for consolidated equipment operations.
 * It follows the service layer pattern to encapsulate business logic and
 * coordinate between multiple domain services.
 *
 * Best Practices:
 * - Encapsulates business logic for equipment aggregation
 * - Uses DTOs for data transfer
 * - Coordinates between multiple domain services
 * - Follows single responsibility principle
 */
@Service
public class EquipmentService {

    private final LaptopService laptopService;
    private final TabletService tabletService;
    private final PhoneService phoneService;

    @Autowired
    public EquipmentService(LaptopService laptopService, TabletService tabletService, PhoneService phoneService) {
        this.laptopService = laptopService;
        this.tabletService = tabletService;
        this.phoneService = phoneService;
    }

    /**
     * Get all equipment from all types (laptops, tablets, smartphones)
     *
     * @return List of EquipmentDTO containing all equipment
     */
    public List<EquipmentDTO> getAllEquipment() {
        List<EquipmentDTO> allEquipment = new ArrayList<>();

        // Add laptops
        laptopService.findAll().forEach(laptop -> {
            EquipmentDTO dto = EquipmentDTO.fromLaptop(
                    laptop.getLaptopId(),
                    laptop.getBrand(),
                    laptop.getModel(),
                    laptop.getCpu(),
                    laptop.getGpu(),
                    laptop.getRam(),
                    laptop.getHd());
            allEquipment.add(dto);
        });

        // Add tablets
        tabletService.findAll().forEach(tablet -> {
            EquipmentDTO dto = EquipmentDTO.fromTablet(
                    tablet.getId(),
                    tablet.getBrand(),
                    tablet.getModel(),
                    tablet.getScreen(),
                    tablet.getCamera(),
                    tablet.getHd());
            allEquipment.add(dto);
        });

        // Add phones
        phoneService.findAll().forEach(phone -> {
            EquipmentDTO dto = EquipmentDTO.fromSmartphone(
                    phone.getId(),
                    phone.getBrand(),
                    phone.getModel(),
                    phone.getScreen(),
                    phone.getCamera(),
                    phone.getHd());
            allEquipment.add(dto);
        });

        return allEquipment;
    }
}
