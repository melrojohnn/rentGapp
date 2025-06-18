# RentApp - Equipment Rental System

**RentApp** is a complete Java Spring Boot application for managing electronic equipment rentals (laptops, tablets, and smartphones). The system includes customer management, subscription plans, order control, and a modern web interface.

## 🚀 Features

### ✅ Implemented
- **Customer Management**: Registration, listing, and customer management
- **Equipment Management**: Laptops, tablets, and smartphones with inventory control
- **Subscription Plans**: Basic (1 equipment), Standard (2 equipment), Premium (3 types)
- **Order System**: Order creation with business rule validation
- **Web Interface**: Complete frontend with Bootstrap for all operations
- **Rule Validation**: Automatic control of equipment per plan
- **REST API**: Complete endpoints for all operations
- **Database**: PostgreSQL with JPA/Hibernate
- **Security**: Basic Spring Security configuration

### 🎯 Business Rules
- **Basic Plan**: Allows renting 1 equipment of any type
- **Standard Plan**: Allows renting up to 2 equipment of any type
- **Premium Plan**: Allows renting equipment of all 3 types
- **Automatic Validation**: System automatically verifies rules during order creation

## 🛠 Tech Stack

- **Backend**: Java 17, Spring Boot 3.5.x
- **Persistence**: Spring Data JPA, Hibernate, PostgreSQL
- **Frontend**: HTML5, CSS3, JavaScript, Bootstrap 5
- **Build**: Maven
- **Security**: Spring Security
- **Utilities**: Lombok

## 📁 Project Structure

```
src/main/java/com/melro/rentapp/
├── config/           # Configurations (Security, etc.)
├── controller/       # REST Controllers
├── dto/             # Data Transfer Objects
├── enums/           # Enumerations (PlanType, EquipmentType, etc.)
├── model/           # JPA Entities
├── repository/      # Spring Data Repositories
├── security/        # Security configurations
└── service/         # Business logic
```

## 🗄️ Database Structure

- `customers`: Customer data
- `internal_users`: Internal system users
- `laptops`: Laptop type equipment
- `tablets`: Tablet type equipment
- `smartphones`: Smartphone type equipment
- `plans`: Available subscription plans
- `orders`: Rental orders
- `order_laptops`, `order_tablets`, `order_smartphones`: Many-to-many relationships

## 🌐 API Endpoints

### Customers
- `GET /api/customers` - List all customers
- `POST /api/customers` - Create new customer
- `GET /api/customers/{id}` - Get customer by ID
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

### Equipment
- `GET /api/equipment` - List all equipment
- `GET /api/laptops` - List laptops
- `GET /api/tablets` - List tablets
- `GET /api/phones` - List smartphones
- `POST /api/laptops` - Create laptop
- `POST /api/tablets` - Create tablet
- `POST /api/phones` - Create smartphone

### Plans
- `GET /api/plans` - List all plans
- `POST /api/plans` - Create new plan

### Orders
- `GET /api/orders` - List all orders
- `POST /api/orders` - Create new order
- `GET /api/orders/{id}` - Get order by ID

### Health
- `GET /api/health` - Check application status

## 🚀 How to Run

### Prerequisites
- Java 17 or higher
- PostgreSQL 14 or higher
- Maven 3.6+

### 1. Database Configuration
```sql
CREATE DATABASE rentapp;
```

### 2. Application Configuration
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/rentapp
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Run the Application
```bash
# Clone the repository
git clone https://github.com/melrojohnn/rentapp.git
cd rentapp

# Run with Maven
mvn spring-boot:run
```

### 4. Access the Application
- **Web Interface**: http://localhost:8099
- **REST API**: http://localhost:8099/api

## 🧪 Testing the API

### Example Order Creation
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

### Example Customer Creation
```bash
curl -X POST http://localhost:8099/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "email": "john@email.com",
    "password": "password123"
  }'
```

## 📱 Web Interface

The application includes a complete web interface with:
- **Dashboard**: System overview
- **Customers**: Complete customer management
- **Equipment**: Management of laptops, tablets, and smartphones
- **Plans**: Subscription plan management
- **Orders**: Order visualization and creation

## 🔧 Configuration

### Application Port
The application runs on port **8099** by default.

### Database
The system uses PostgreSQL with the following default configurations:
- Host: localhost
- Port: 5432
- Database: rentapp

## 📋 TODO

- [ ] Implement JWT authentication
- [ ] Add Swagger/OpenAPI documentation
- [ ] Implement unit and integration tests
- [ ] Add structured logging
- [ ] Implement Redis cache
- [ ] Configure CI/CD with GitHub Actions
- [ ] Dockerize the application
- [ ] Implement email notifications
- [ ] Add reports and dashboards

## 👤 Author

**Melro Johnn**
- GitHub: [@melrojohnn](https://github.com/melrojohnn)

## 📄 License

This project is under the MIT license. See the [LICENSE](LICENSE) file for more details.

## 🤝 Contributing

Contributions are welcome! Please read the contribution guidelines before submitting a pull request.

---

**RentApp** - Simplifying electronic equipment rental! 🚀


