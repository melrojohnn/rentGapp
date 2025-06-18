package com.melro.rentapp.model;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.melro.rentapp.enums.PlanDuration;
import com.melro.rentapp.enums.PlanType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a rental plan in the system.
 *
 * This entity defines the available rental plans that customers can choose
 * from.
 * Each plan has a specific duration, price, and description. Plans are used
 * to standardize rental offerings and calculate order end dates automatically.
 *
 * Business Rules:
 * - Each plan has a unique name
 * - Plans have predefined durations (3, 6, or 12 months)
 * - Plans have specific types (BASIC, STANDARD, PREMIUM) with equipment rules
 * - Prices are stored with 2 decimal places for currency precision
 * - Creation and update timestamps are automatically managed
 * - Plans can be referenced by multiple orders
 *
 * This entity is used in relationships with OrderModel to define the rental
 * terms and automatically calculate order end dates based on plan duration.
 */
@Entity
@Table(name = "tb_plan")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlansModel {

    /**
     * Unique identifier for the plan.
     * Auto-generated using database identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the rental plan.
     * Must be unique across all plans and cannot be null.
     * Limited to 50 characters for database efficiency.
     */
    @Column(name = "plan_name", nullable = false, unique = true, length = 50)
    private String name;

    /**
     * Monthly price for the rental plan.
     * Stored with 2 decimal places for currency precision.
     * Cannot be null and must be a positive value.
     */
    @Column(name = "price", nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    /**
     * Description of the plan features and benefits.
     * Optional field limited to 255 characters.
     */
    @Column(name = "description", length = 255)
    private String description;

    /**
     * Duration of the rental plan.
     * Defines how long the rental period lasts (3, 6, or 12 months).
     * This value is used to automatically calculate order end dates.
     * Stored as a string enum value for readability.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "duration", nullable = false, length = 20)
    private PlanDuration duration;

    /**
     * Type of the rental plan.
     * Defines the equipment rules and limitations (BASIC, STANDARD, PREMIUM).
     * This value is used for validation during order creation.
     * Stored as a string enum value for readability.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type", nullable = false, length = 20)
    private PlanType planType;

    /**
     * Timestamp when the plan was created.
     * Automatically set by Hibernate when the entity is first persisted.
     * Cannot be updated after creation.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Timestamp when the plan was last updated.
     * Automatically updated by Hibernate whenever the entity is modified.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
