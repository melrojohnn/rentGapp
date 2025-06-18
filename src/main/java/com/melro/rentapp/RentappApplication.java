package com.melro.rentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for the Rental Application.
 *
 * This application manages a rental system for electronic devices (laptops,
 * phones, tablets)
 * with customer management, plan subscriptions, and order processing
 * capabilities.
 *
 * Key features:
 * - Device rental management (laptops, smartphones, tablets)
 * - Customer registration and management
 * - Subscription plans with different durations
 * - Order processing and tracking
 * - Internal user management for administrative tasks
 *
 * @SpringBootApplication enables auto-configuration, component scanning, and
 *                        configuration
 *                        properties support for the entire application.
 */
@SpringBootApplication
public class RentappApplication {

	/**
	 * Main entry point for the Spring Boot application.
	 *
	 * Starts the embedded web server and initializes the Spring application
	 * context.
	 * The application will be available at http://localhost:8080 by default.
	 *
	 * @param args Command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(RentappApplication.class, args);
	}

}
