# RentGapp - Complete Equipment Rental System

**RentGapp** is a comprehensive Java Spring Boot application for managing electronic equipment rentals (laptops, tablets, and smartphones). The system includes customer management, subscription plans, order control with business rule validation, and a modern web interface.

## 🚀 Features

### ✅ Implemented (v2.0.0)
- **Customer Management**: Complete CRUD operations with web interface
- **Equipment Management**: Laptops, tablets, and smartphones with inventory control
- **Subscription Plans**: Basic (1 equipment), Standard (2 equipment), Premium (3 types)
- **Order System**: Complete order creation with automatic business rule validation
- **Web Interface**: Modern frontend with Bootstrap 5 and JavaScript
- **Business Rule Engine**: Automatic validation of plan-based equipment limits
- **REST API**: Complete endpoints for all operations
- **Database**: PostgreSQL with JPA/Hibernate and proper relationships
- **Security**: Spring Security configuration
- **Validation Service**: Dedicated service for business rule enforcement

### 🎯 Business Rules
- **Basic Plan**: Allows renting 1 equipment of any type (laptop, tablet, or smartphone)
- **Standard Plan**: Allows renting up to 2 equipment of any type
- **Premium Plan**: Allows renting equipment of all 3 types (laptop + tablet + smartphone)
- **Automatic Validation**: System automatically verifies rules during order creation
- **Equipment Type Control**: Validates equipment types against plan restrictions

## 🛠 Tech Stack

- **Backend**: Java 17, Spring Boot 3.5.x
- **Persistence**: Spring Data JPA, Hibernate, PostgreSQL
- **Frontend**: HTML5, CSS3, JavaScript, Bootstrap 5, Thymeleaf
- **Build**: Maven
- **Security**: Spring Security
- **Utilities**: Lombok
- **Database**: PostgreSQL 14+

## 📁 Project Structure

```
src/main/java/com/melro/rentapp/
├── config/           # Configurations (Security, etc.)
├── controller/       # REST Controllers (API + Web)
├── dto/             # Data Transfer Objects
├── enums/           # Enumerations (PlanType, EquipmentType, etc.)
├── model/           # JPA Entities
├── repository/      # Spring Data Repositories
├── security/        # Security configurations
└── service/         # Business logic and validation
```

## 🗄️ Database Structure

- `customers`: Customer data with authentication
- `internal_users`: Internal system users
- `laptops`: Laptop type equipment
- `tablets`: Tablet type equipment
- `smartphones`: Smartphone type equipment
- `plans`: Available subscription plans with types and durations
- `orders`: Rental orders with timestamps
- `order_laptops`, `order_tablets`, `order_smartphones`: Many-to-many relationships

## 🌐 API Endpoints

### Web Interface
- `GET /` - Main dashboard
- `GET /customers` - Customer management page
- `GET /equipment` - Equipment management page
- `GET /plans` - Plan management page
- `GET /orders` - Order management page

### REST API
- `GET /api/health` - Check application status

#### Customers
- `GET /api/customers` - List all customers
- `POST /api/customers` - Create new customer
- `GET /api/customers/{id}` - Get customer by ID
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

#### Equipment
- `GET /api/equipment` - List all equipment
- `GET /api/laptops` - List laptops
- `GET /api/tablets` - List tablets
- `GET /api/phones` - List smartphones
- `POST /api/laptops` - Create laptop
- `POST /api/tablets` - Create tablet
- `POST /api/phones` - Create smartphone

#### Plans
- `GET /api/plans` - List all plans
- `POST /api/plans` - Create new plan

#### Orders
- `GET /api/orders` - List all orders
- `POST /api/orders` - Create new order with validation
- `GET /api/orders/{id}` - Get order by ID

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- PostgreSQL 14 or higher
- Maven 3.6+

### 1. Clone and Setup
```bash
# Clone the repository
git clone https://github.com/melrojohnn/rentGapp.git
cd rentGapp

# Checkout the latest version
git checkout v2.0.0
```

### 2. Database Setup
```sql
-- Create database
CREATE DATABASE rentapp;

-- The application will automatically create tables and populate data
-- on first startup using the SQL scripts in src/main/resources/
```

### 3. Configuration
Edit `src/main/resources/application.properties`:
```properties
# Database configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/rentapp
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Server configuration
server.port=8099
```

### 4. Run the Application
```bash
# Run with Maven
mvn spring-boot:run

# Or build and run JAR
mvn clean package
java -jar target/rentapp-0.0.1-SNAPSHOT.jar
```

### 5. Access the Application
- **Web Interface**: http://localhost:8099
- **REST API**: http://localhost:8099/api
- **Health Check**: http://localhost:8099/api/health

## 🧪 Testing the Application

### Using the Web Interface
1. Open http://localhost:8099 in your browser
2. Navigate through the tabs: Dashboard, Customers, Equipment, Plans, Orders
3. Use the forms to create and manage data
4. Test order creation with different plan combinations

### Using the REST API

#### Create a Customer
```bash
curl -X POST http://localhost:8099/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "email": "john@email.com",
    "password": "password123"
  }'
```

#### Create an Order (Basic Plan - 1 equipment)
```bash
curl -X POST http://localhost:8099/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "planId": 1,
    "equipmentItems": [
      {"equipmentId": 1, "equipmentType": "LAPTOP"}
    ]
  }'
```

#### Create an Order (Standard Plan - 2 equipment)
```bash
curl -X POST http://localhost:8099/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "planId": 2,
    "equipmentItems": [
      {"equipmentId": 1, "equipmentType": "LAPTOP"},
      {"equipmentId": 2, "equipmentType": "TABLET"}
    ]
  }'
```

#### Create an Order (Premium Plan - 3 equipment)
```bash
curl -X POST http://localhost:8099/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "planId": 3,
    "equipmentItems": [
      {"equipmentId": 1, "equipmentType": "LAPTOP"},
      {"equipmentId": 2, "equipmentType": "TABLET"},
      {"equipmentId": 3, "equipmentType": "SMARTPHONE"}
    ]
  }'
```

### Using the Test Script
```bash
# Make the script executable
chmod +x test_order_creation.sh

# Run the test
./test_order_creation.sh
```

## 📱 Web Interface Features

The application includes a complete web interface with:
- **Dashboard**: System overview with statistics
- **Customers**: Complete customer management with forms
- **Equipment**: Management of laptops, tablets, and smartphones
- **Plans**: Subscription plan management
- **Orders**: Order visualization and creation with validation

## 🔧 Configuration Options

### Application Properties
```properties
# Server
server.port=8099

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/rentapp
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.com.melro.rentapp=DEBUG
```

### Environment Variables
```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/rentapp
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password
export SERVER_PORT=8099
```

## 🐛 Troubleshooting

### Common Issues

1. **Port already in use (8099)**
   ```bash
   # Find and kill the process
   lsof -ti:8099 | xargs kill -9
   ```

2. **Database connection failed**
   - Verify PostgreSQL is running
   - Check database credentials
   - Ensure database exists

3. **Application won't start**
   - Check Java version (requires 17+)
   - Verify Maven dependencies
   - Check application logs

### Logs
```bash
# View application logs
tail -f logs/application.log

# Or if using Maven
mvn spring-boot:run -Dspring-boot.run.arguments="--logging.level.com.melro.rentapp=DEBUG"
```

## 📋 Development Roadmap

### v2.1.0 (Planned)
- [ ] JWT authentication and authorization
- [ ] User roles and permissions
- [ ] API rate limiting

### v2.2.0 (Planned)
- [ ] Swagger/OpenAPI documentation
- [ ] API versioning
- [ ] Enhanced error handling

### v2.3.0 (Planned)
- [ ] Unit and integration tests
- [ ] CI/CD with GitHub Actions
- [ ] Code coverage reports

### v2.4.0 (Planned)
- [ ] Dockerization
- [ ] Kubernetes deployment
- [ ] Monitoring and metrics

## 👤 Author

**Melro Johnn**
- GitHub: [@melrojohnn](https://github.com/melrojohnn)
- Project: [RentGapp](https://github.com/melrojohnn/rentGapp)

## 📄 License

This project is under the MIT license. See the [LICENSE](LICENSE) file for more details.

## 🤝 Contributing

Contributions are welcome! Please read the contribution guidelines before submitting a pull request.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📞 Support

If you encounter any issues or have questions:
1. Check the [Issues](https://github.com/melrojohnn/rentGapp/issues) page
2. Create a new issue with detailed information
3. Include logs and error messages

---

**RentGapp v2.0.0** - Complete electronic equipment rental system! 🚀


