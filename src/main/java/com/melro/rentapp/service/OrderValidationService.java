package com.melro.rentapp.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.melro.rentapp.dto.OrderRequestDTO;
import com.melro.rentapp.enums.EquipmentType;
import com.melro.rentapp.enums.PlanType;
import com.melro.rentapp.model.PlansModel;

/**
 * Service responsible for validating order creation against business rules.
 *
 * This service ensures that orders comply with plan restrictions and equipment
 * limitations before they are created.
 *
 * Business Rules:
 * - Equipment count must match plan type restrictions
 * - Equipment types must be allowed by the plan
 * - Premium plan must include one of each equipment type
 * - Basic plan can only have one equipment item
 * - Standard plan can have up to two equipment items
 */
@Service
public class OrderValidationService {

    /**
     * Validates an order request against the plan rules.
     *
     * @param orderRequest The order request to validate
     * @param plan         The plan associated with the order
     * @throws IllegalArgumentException if validation fails
     */
    public void validateOrder(OrderRequestDTO orderRequest, PlansModel plan) {
        List<OrderRequestDTO.EquipmentItemDTO> equipmentItems = orderRequest.equipmentItems();
        PlanType planType = plan.getPlanType();

        // Validate equipment count
        validateEquipmentCount(equipmentItems.size(), planType);

        // Validate equipment types
        validateEquipmentTypes(equipmentItems, planType);

        // Validate specific plan rules
        validatePlanSpecificRules(equipmentItems, planType);
    }

    /**
     * Validates that the equipment count matches the plan restrictions.
     *
     * @param equipmentCount Number of equipment items
     * @param planType       Type of plan being validated
     * @throws IllegalArgumentException if count is invalid
     */
    private void validateEquipmentCount(int equipmentCount, PlanType planType) {
        if (!planType.isValidEquipmentCount(equipmentCount)) {
            throw new IllegalArgumentException(
                    String.format("Plan type %s allows maximum %d equipment items, but %d were provided",
                            planType, planType.getMaxEquipmentCount(), equipmentCount));
        }
    }

    /**
     * Validates that all equipment types are allowed by the plan.
     *
     * @param equipmentItems List of equipment items to validate
     * @param planType       Type of plan being validated
     * @throws IllegalArgumentException if any equipment type is not allowed
     */
    private void validateEquipmentTypes(List<OrderRequestDTO.EquipmentItemDTO> equipmentItems, PlanType planType) {
        for (OrderRequestDTO.EquipmentItemDTO item : equipmentItems) {
            if (!planType.isEquipmentTypeAllowed(item.equipmentType())) {
                throw new IllegalArgumentException(
                        String.format("Equipment type %s is not allowed in plan type %s",
                                item.equipmentType(), planType));
            }
        }
    }

    /**
     * Validates plan-specific rules (e.g., Premium plan must have one of each
     * type).
     *
     * @param equipmentItems List of equipment items to validate
     * @param planType       Type of plan being validated
     * @throws IllegalArgumentException if plan-specific rules are violated
     */
    private void validatePlanSpecificRules(List<OrderRequestDTO.EquipmentItemDTO> equipmentItems, PlanType planType) {
        if (planType == PlanType.PREMIUM) {
            validatePremiumPlanRules(equipmentItems);
        }
    }

    /**
     * Validates Premium plan rules - must have exactly one of each equipment type.
     *
     * @param equipmentItems List of equipment items to validate
     * @throws IllegalArgumentException if Premium plan rules are violated
     */
    private void validatePremiumPlanRules(List<OrderRequestDTO.EquipmentItemDTO> equipmentItems) {
        Map<EquipmentType, Long> equipmentTypeCounts = equipmentItems.stream()
                .collect(Collectors.groupingBy(
                        OrderRequestDTO.EquipmentItemDTO::equipmentType,
                        Collectors.counting()));

        // Premium plan must have exactly one of each type
        if (equipmentTypeCounts.size() != 3 ||
                equipmentTypeCounts.values().stream().anyMatch(count -> count != 1)) {
            throw new IllegalArgumentException(
                    "Premium plan requires exactly one laptop, one tablet, and one smartphone");
        }
    }
}
