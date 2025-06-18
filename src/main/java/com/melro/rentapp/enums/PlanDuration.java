package com.melro.rentapp.enums;

/**
 * Enumeration representing the available rental plan durations.
 *
 * This enum defines the standard rental periods that customers can choose from
 * when creating a rental order. The duration affects both pricing and the
 * automatic calculation of order end dates.
 *
 * Business Rules:
 * - THREE_MONTHS: 3-month rental period
 * - SIX_MONTHS: 6-month rental period
 * - TWELVE_MONTHS: 12-month rental period
 *
 * These values are used in the PlansModel to define plan characteristics
 * and in the OrderModel's @PrePersist method to automatically calculate
 * order end dates based on the selected plan duration.
 */
public enum PlanDuration {
    /**
     * Three-month rental period.
     * Used for short-term equipment rentals.
     */
    THREE_MONTHS,

    /**
     * Six-month rental period.
     * Standard medium-term rental option.
     */
    SIX_MONTHS,

    /**
     * Twelve-month rental period.
     * Long-term rental option, typically with better pricing.
     */
    TWELVE_MONTHS
}
