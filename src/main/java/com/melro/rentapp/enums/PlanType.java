package com.melro.rentapp.enums;

import java.util.Set;

/**
 * Enumeration representing the different types of rental plans and their
 * equipment rules.
 *
 * This enum defines the business rules for each plan type regarding which
 * equipment
 * can be included and how many of each type are allowed.
 *
 * Business Rules:
 * - BASIC: Only 1 equipment (laptop, tablet, or smartphone)
 * - STANDARD: 2 equipment items (any combination of laptop, tablet, smartphone)
 * - PREMIUM: 3 equipment items (laptop, tablet, and smartphone)
 */
public enum PlanType {
    /**
     * Basic plan - allows only 1 piece of equipment.
     * Customer can choose between laptop, tablet, or smartphone.
     */
    BASIC(1, Set.of(EquipmentType.LAPTOP, EquipmentType.TABLET, EquipmentType.SMARTPHONE)),

    /**
     * Standard plan - allows 2 pieces of equipment.
     * Customer can choose any combination of laptop, tablet, and smartphone.
     */
    STANDARD(2, Set.of(EquipmentType.LAPTOP, EquipmentType.TABLET, EquipmentType.SMARTPHONE)),

    /**
     * Premium plan - allows 3 pieces of equipment.
     * Customer gets laptop, tablet, and smartphone (one of each).
     */
    PREMIUM(3, Set.of(EquipmentType.LAPTOP, EquipmentType.TABLET, EquipmentType.SMARTPHONE));

    private final int maxEquipmentCount;
    private final Set<EquipmentType> allowedEquipmentTypes;

    /**
     * Constructor for plan types.
     *
     * @param maxEquipmentCount     Maximum number of equipment items allowed
     * @param allowedEquipmentTypes Set of equipment types allowed in this plan
     */
    PlanType(int maxEquipmentCount, Set<EquipmentType> allowedEquipmentTypes) {
        this.maxEquipmentCount = maxEquipmentCount;
        this.allowedEquipmentTypes = allowedEquipmentTypes;
    }

    /**
     * Gets the maximum number of equipment items allowed in this plan.
     *
     * @return Maximum equipment count
     */
    public int getMaxEquipmentCount() {
        return maxEquipmentCount;
    }

    /**
     * Gets the set of equipment types allowed in this plan.
     *
     * @return Set of allowed equipment types
     */
    public Set<EquipmentType> getAllowedEquipmentTypes() {
        return allowedEquipmentTypes;
    }

    /**
     * Validates if the given equipment count is valid for this plan.
     *
     * @param equipmentCount Number of equipment items to validate
     * @return true if the count is valid for this plan
     */
    public boolean isValidEquipmentCount(int equipmentCount) {
        return equipmentCount <= maxEquipmentCount && equipmentCount > 0;
    }

    /**
     * Validates if the given equipment type is allowed in this plan.
     *
     * @param equipmentType Type of equipment to validate
     * @return true if the equipment type is allowed in this plan
     */
    public boolean isEquipmentTypeAllowed(EquipmentType equipmentType) {
        return allowedEquipmentTypes.contains(equipmentType);
    }
}
