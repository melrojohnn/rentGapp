// OrderRequestDTO.java
package com.melro.rentapp.dto;

import java.util.List;

import com.melro.rentapp.enums.EquipmentType;

/**
 * Data Transfer Object (DTO) for creating new rental orders.
 *
 * This record represents the request payload for creating a rental order.
 * It contains all the necessary information to create an order: customer ID,
 * plan ID, and equipment details with their types and IDs.
 *
 * Business Rules:
 * - customerId must reference an existing customer in the system
 * - planId must reference an existing rental plan
 * - equipmentItems must contain valid equipment with existing IDs
 * - Equipment count and types must match the plan restrictions
 * - At least one equipment item must be specified
 *
 * This DTO is used in the POST /v1/order endpoint to create new orders.
 *
 * @param customerId     The unique identifier of the customer placing the order
 * @param planId         The unique identifier of the rental plan to be used
 * @param equipmentItems List of equipment items with their types and IDs
 */
public record OrderRequestDTO(
        Long customerId,
        Long planId,
        List<EquipmentItemDTO> equipmentItems) {
    /**
     * Nested DTO for equipment items in an order.
     *
     * @param equipmentType The type of equipment (LAPTOP, TABLET, SMARTPHONE)
     * @param equipmentId   The unique identifier of the specific equipment
     */
    public record EquipmentItemDTO(
            EquipmentType equipmentType,
            Long equipmentId) {
    }
}
