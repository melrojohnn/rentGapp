package com.melro.rentapp.dto;

import com.melro.rentapp.enums.EquipmentType;

/**
 * Data Transfer Object for Equipment
 *
 * This DTO provides a unified view of all equipment types (laptops, tablets,
 * smartphones)
 * for the frontend application, following the DTO pattern to separate API
 * contracts
 * from domain entities.
 */
public record EquipmentDTO(
        Long id,
        String name,
        EquipmentType type,
        boolean available,
        String brand,
        String model,
        String specifications) {

    /**
     * Creates an EquipmentDTO for a laptop
     */
    public static EquipmentDTO fromLaptop(Long id, String brand, String model, String cpu, String gpu, String ram,
            String hd) {
        return new EquipmentDTO(
                id,
                brand + " " + model,
                EquipmentType.LAPTOP,
                true, // Assuming available by default
                brand,
                model,
                "CPU: " + cpu + ", GPU: " + gpu + ", RAM: " + ram + ", HD: " + hd);
    }

    /**
     * Creates an EquipmentDTO for a tablet
     */
    public static EquipmentDTO fromTablet(Long id, String brand, String model, String screen, String camera,
            String hd) {
        return new EquipmentDTO(
                id,
                brand + " " + model,
                EquipmentType.TABLET,
                true, // Assuming available by default
                brand,
                model,
                "Screen: " + screen + ", Camera: " + camera + ", HD: " + hd);
    }

    /**
     * Creates an EquipmentDTO for a smartphone
     */
    public static EquipmentDTO fromSmartphone(Long id, String brand, String model, String screen, String camera,
            String hd) {
        return new EquipmentDTO(
                id,
                brand + " " + model,
                EquipmentType.SMARTPHONE,
                true, // Assuming available by default
                brand,
                model,
                "Screen: " + screen + ", Camera: " + camera + ", HD: " + hd);
    }
}
