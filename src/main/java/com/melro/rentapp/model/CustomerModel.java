package com.melro.rentapp.model;

import com.melro.rentapp.model.common.UsersData;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entity representing a customer in the rental system.
 *
 * This entity extends UsersData to inherit common user properties like name,
 * email, and password. Customers are the end users who place rental orders
 * for equipment in the system.
 *
 * Business Rules:
 * - Customers can place multiple orders
 * - Each customer has a unique email address
 * - Customer data is required for order creation
 * - Customers have access to their own order history and profile
 *
 * This entity is used in relationships with OrderModel to track which
 * customer placed each order.
 */
@Entity
@Table(name = "tb_customer")
public class CustomerModel extends UsersData {

    /**
     * Default constructor required by JPA.
     * Creates an empty customer instance.
     */
    public CustomerModel() {
        super();
    }

    /**
     * Constructor for creating a customer with basic information.
     *
     * @param name     The customer's full name
     * @param email    The customer's email address (must be unique)
     * @param password The customer's password (will be encrypted)
     */
    public CustomerModel(String name, String email, String password) {
        super(name, email, password);
    }

}
