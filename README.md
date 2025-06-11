# RentGapp

**RentGapp** is a Java-based Spring Boot application designed to manage the rental of electronic gadgets like laptops, phones, and tablets. It includes user management, device inventory, and subscription plans.

## 🚀 Features

- ✅ User registration and authentication
- 💻 Device management: laptops, phones, tablets
- 💸 Subscription plans: Basic, Standard, Premium
- 🧾 PostgreSQL integration with JPA/Hibernate
- 🔐 Password encryption with BCrypt
- 🕓 Automatic timestamps on entity creation/update

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.5.x
- Spring Data JPA
- PostgreSQL
- Lombok
- Hibernate
- Maven

## 📦 Database Structure

- `tb_member`: stores user data
- `tb_laptop`, `tb_phone`, `tb_tablet`: device-specific tables
- `tb_plan`: stores available subscription plans

## 📄 API Endpoints (WIP)

| Method | Endpoint     | Description           |
|--------|--------------|-----------------------|
| POST   | `/login`     | User login            |
| POST   | `/users`     | Create new user       |
| GET    | `/laptops`   | List all laptops      |
| POST   | `/plans`     | Add a subscription    |
| ...    |              | More coming soon...   |

## 📚 Getting Started

1. Clone the repository:
   ```bash
   git clone git@github.com:melrojohnn/rentGapp.git
   ```

   Configure your PostgreSQL database in application.properties.

Run the application:

 ```bash
./mvnw spring-boot:run
```

🧪 Testing the API
You can use Insomnia or Postman to test endpoints.

📌 TODO
Add JWT authentication

Implement device availability and rental logic

Add Swagger documentation

CI/CD with Jenkins and Docker

👤 Author
Melro Johnn
GitHub: @melrojohnn


