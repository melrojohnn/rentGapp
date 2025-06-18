package com.melro.rentapp.enums;

/**
 * Enumeration representing the different user roles in the rental system.
 *
 * This enum defines the hierarchy of user permissions and access levels
 * within the application. Each role has specific capabilities and restrictions
 * for accessing different parts of the system.
 *
 * Business Rules:
 * - CUSTOMER: Basic user who can place orders and view their own data
 * - INTERNAL_USER: Internal staff member with limited administrative access
 * - SALES: Sales representative with order management capabilities
 * - SUPPORT: Customer support staff with customer service access
 * - MANAGER: Management level with broader administrative access
 * - ADMIN: Full system administrator with complete access
 *
 * These roles are used for authentication, authorization, and access control
 * throughout the application, particularly in the security configuration.
 */
public enum UserRole {
    /**
     * Customer role - basic user who can place rental orders.
     * Has access to their own profile and order history.
     */
    CUSTOMER,

    /**
     * Internal user role - staff member with basic administrative access.
     * Can view and manage orders, customers, and equipment.
     */
    INTERNAL_USER,

    /**
     * Sales role - sales representative with order management capabilities.
     * Can create, modify, and manage customer orders and accounts.
     */
    SALES,

    /**
     * Support role - customer support staff.
     * Can access customer information and provide support services.
     */
    SUPPORT,

    /**
     * Manager role - management level access.
     * Can manage staff, view reports, and handle escalated issues.
     */
    MANAGER,

    /**
     * Admin role - full system administrator.
     * Has complete access to all system features and configurations.
     */
    ADMIN
}
