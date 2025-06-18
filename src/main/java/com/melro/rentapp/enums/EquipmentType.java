package com.melro.rentapp.enums;

/**
 * Enumeration representing the different types of equipment available for
 * rental.
 *
 * This enum defines the equipment categories that can be included in rental
 * orders.
 * Each type corresponds to a specific entity model in the system.
 *
 * Business Rules:
 * - LAPTOP: Desktop and portable computers
 * - TABLET: Touchscreen tablets and iPads
 * - SMARTPHONE: Mobile phones and smartphones
 *
 * These types are used for validation and business logic in order processing.
 */
public enum EquipmentType {
    /**
     * Laptop equipment - desktop and portable computers.
     * Includes notebooks, laptops, and desktop computers.
     */
    LAPTOP,

    /**
     * Tablet equipment - touchscreen tablets.
     * Includes iPads, Android tablets, and other touchscreen devices.
     */
    TABLET,

    /**
     * Smartphone equipment - mobile phones.
     * Includes iPhones, Android phones, and other mobile devices.
     */
    SMARTPHONE
}
