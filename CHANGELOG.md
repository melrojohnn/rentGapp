# Changelog

All important changes in this project will be documented here.

## [2.0.0] - 2025-01-27
### Added
- **Complete Rental System**: Full-featured rental management application
- **Order Management System**: Complete order creation, validation, and tracking
- **Business Rule Engine**: Automatic validation of plan-based equipment limits
- **Equipment Type System**: Unified management of laptops, phones, and tablets
- **Plan Validation**: Enforces Basic (1 equipment), Standard (2 equipment), Premium (3 equipment) rules
- **Frontend Interface**: Modern web interface with Bootstrap 5 and JavaScript
- **OrderValidationService**: Dedicated service for business rule enforcement
- **EquipmentController**: Unified controller for all equipment types
- **FrontendController**: Dedicated controller for web interface
- **Enhanced DTOs**: OrderRequestDTO, OrderResponseDTO, EquipmentDTO
- **Comprehensive Enums**: EquipmentType, PlanType, PlanDuration with business logic
- **Database Schema**: Complete schema with proper relationships and constraints
- **Test Scripts**: Automated testing scripts for order creation
- **Documentation**: Complete English documentation and comments

### Changed
- **OrderModel**: Complete redesign to support multiple equipment types and validation
- **OrderService**: Enhanced with validation logic and equipment loading
- **Database Structure**: Updated schema to support complex order relationships
- **API Endpoints**: Improved REST endpoints with proper validation
- **Frontend**: Complete redesign with modern UI and interactive features
- **README.md**: Updated with comprehensive project information
- **pom.xml**: Updated dependencies and project metadata

### Technical Improvements
- **Java 17**: Latest LTS version
- **Spring Boot 3.5.x**: Updated framework version
- **PostgreSQL**: Optimized database configuration
- **JPA/Hibernate**: Enhanced entity relationships
- **Maven**: Updated dependency management
- **Security**: Improved configuration and validation

### API Endpoints
- `POST /orders` - Create orders with automatic validation
- `GET /orders` - List all orders with details
- `GET /customers` - Customer management
- `GET /plans` - Plan information
- `GET /equipment` - Equipment listing
- `GET /` - Web interface

## [1.2.0] - 2025-06-17
### Added
- **Complete Web Interface**: Modern frontend with Bootstrap 5
- **Interactive Dashboard**: System overview with statistics
- **Customer Management**: Complete CRUD via web interface
- **Equipment Management**: Interface for laptops, tablets, and smartphones
- **Plan Management**: Interface for subscription plans
- **Order Management**: Order visualization and creation via web
- **FrontendController**: Dedicated controller to serve web interface
- **Business Rule Validation**: Automatic order validation system
- **OrderValidationService**: Service to validate equipment rules per plan

### Changed
- **HealthCheckController**: Moved to `/api/health` to avoid frontend conflicts
- **EquipmentController**: Refactored to use consolidated EquipmentService
- **OrderModel**: Updated to support multiple equipment types
- **OrderService**: Improved to load all equipment types
- **Frontend**: Removed conflicting static file, using Thymeleaf template

### Fixed
- **Port 8099**: Resolved port conflict during startup
- **Frontend Data**: Fixed issue with data not appearing in tables
- **Forms**: Fixed "phone" field to "password" in customer form
- **Table Structure**: Added complete sections for all tabs

## [1.1.0] - 2025-06-17
### Added
- **Complete Order System**: Creation and management of rental orders
- **Business Rule Validation**: Automatic control of equipment per plan
- **OrderRequestDTO**: DTO for order creation with validation
- **OrderResponseDTO**: DTO for order responses
- **OrderValidationService**: Service to validate business rules
- **Enums**: PlanType, EquipmentType, PlanDuration, UserRole
- **Many-to-Many Relationships**: Between orders and equipment
- **Test Scripts**: Shell scripts to test order creation

### Changed
- **OrderModel**: Updated structure to support multiple equipment
- **Database Schema**: Added necessary columns for orders
- **SQL Scripts**: Updated with complete test data

### Fixed
- **Data Integrity**: Fixed issue with null required fields
- **Order End Date**: Implemented automatic end date calculation
- **Equipment Loading**: Fixed loading of all equipment types

## [1.0.0] - 2025-06-16
### Added
- **Base Structure**: Initial Spring Boot project configuration
- **Data Models**: CustomerModel, InternalUserModel, EquipmentModels
- **REST Controllers**: Endpoints for all entities
- **Repositories**: Spring Data JPA for persistence
- **Services**: Business logic for all operations
- **Security Configuration**: Basic Spring Security
- **Database**: PostgreSQL with JPA/Hibernate
- **DTOs**: Data Transfer Objects for APIs
- **Comments**: Complete documentation in English

### Technical
- **Java 17**: LTS version of Java
- **Spring Boot 3.5.x**: Main framework
- **PostgreSQL**: Relational database
- **Maven**: Dependency manager
- **Lombok**: Boilerplate code reduction

## [0.1.0] - 2025-06-10
### Added
- **Project Initialization**: Basic Spring Boot setup
- **Maven Configuration**: Initial dependencies
- **Folder Structure**: Project organization

---

## Release Notes

### v2.0.0 - Complete Rental System
This major version represents the complete implementation of the rental system with full business logic, validation, and modern web interface. The application is now production-ready with comprehensive order management, plan validation, and user-friendly interface.

### v1.2.0 - Complete Web Interface
This version represents an important milestone with the implementation of a complete and functional web interface. The system now offers a modern and intuitive user experience for all operations.

### v1.1.0 - Order System
Implementation of the system's core business with automatic business rule validation and complete order management.

### v1.0.0 - Functional MVP
Minimum viable product with all basic functionalities implemented and tested.

---

**Planned Next Versions:**
- v2.1.0: JWT authentication and authorization
- v2.2.0: Swagger/OpenAPI documentation
- v2.3.0: Automated tests and CI/CD
- v2.4.0: Dockerization and deployment
- v3.0.0: Microservices architecture
