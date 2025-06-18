package com.melro.rentapp.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a rental order in the system.
 *
 * An order connects a customer with a specific rental plan and the equipment
 * they want to rent.
 * Each order has an automatic start date (when created) and an end date
 * calculated based on the plan duration.
 *
 * Business Rules:
 * - Every order must have a customer, plan, and at least one piece of equipment
 * - Order date is automatically set to the current timestamp when created
 * - End date is automatically calculated based on the plan duration (3, 6, or
 * 12 months)
 * - Orders can contain multiple pieces of equipment (laptops, phones, tablets)
 * - The relationship with equipment is many-to-many to allow flexible order
 * composition
 */
@Entity
@Table(name = "tb_order")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderModel {

    /**
     * Unique identifier for the order.
     * Auto-generated using database identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Timestamp when the order was created.
     * Automatically set by Hibernate when the entity is persisted.
     * Cannot be updated after creation.
     */
    @CreationTimestamp
    @Column(name = "order_date", nullable = false, updatable = false)
    private Instant orderDate;

    /**
     * Timestamp when the rental period ends.
     * Calculated automatically based on the plan duration and order date.
     */
    @Column(name = "order_end_date", nullable = false)
    private Instant orderEndDate;

    /**
     * Customer who placed this order.
     * Many orders can belong to one customer (Many-to-One relationship).
     * This field is required and cannot be null.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    /**
     * Rental plan associated with this order.
     * Many orders can use the same plan (Many-to-One relationship).
     * This field is required and cannot be null.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private PlansModel plan;

    /**
     * List of laptop equipment included in this order.
     * Many orders can contain many laptops (Many-to-Many relationship).
     */
    @ManyToMany
    @JoinTable(name = "order_laptops", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "laptop_id"))
    private List<LaptopModel> laptops;

    /**
     * List of tablet equipment included in this order.
     * Many orders can contain many tablets (Many-to-Many relationship).
     */
    @ManyToMany
    @JoinTable(name = "order_tablets", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "tablet_id"))
    private List<TabletModel> tablets;

    /**
     * List of smartphone equipment included in this order.
     * Many orders can contain many smartphones (Many-to-Many relationship).
     */
    @ManyToMany
    @JoinTable(name = "order_smartphones", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "smartphone_id"))
    private List<SmartphoneModel> smartphones;

    /**
     * Automatically calculates the end date based on the plan's duration.
     * This method is called by Hibernate before persisting the entity.
     *
     * Business Logic:
     * - Converts the order date to LocalDate for easier date manipulation
     * - Uses a switch expression to add the appropriate number of months based on
     * plan duration
     * - Converts the calculated end date back to Instant for storage
     * - Only calculates if both plan and orderDate are available
     */
    @PrePersist
    public void calculateEndDate() {
        if (plan != null && orderDate != null) {
            LocalDate startDate = orderDate.atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = switch (plan.getDuration()) {
                case THREE_MONTHS -> startDate.plusMonths(3);
                case SIX_MONTHS -> startDate.plusMonths(6);
                case TWELVE_MONTHS -> startDate.plusMonths(12);
            };
            this.orderEndDate = endDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        }
    }
}
