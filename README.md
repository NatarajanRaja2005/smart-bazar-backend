#  Smart Bazar Backend

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Spring Security](https://img.shields.io/badge/Security-Spring%20Security-green)
![JWT](https://img.shields.io/badge/Auth-JWT-blue)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![Swagger](https://img.shields.io/badge/API-Swagger-success)
![License](https://img.shields.io/badge/License-MIT-yellow)

Production-ready E-Commerce Backend built using **Spring Boot**, **Spring Security**, **JWT Authentication**, **Hibernate**, **MySQL**, and **RESTful APIs**.

---

#  Project Overview

Smart Bazar Backend is a production-style e-commerce backend application developed using Spring Boot. It provides secure RESTful APIs for managing products, categories, shopping carts, orders, user authentication, and image uploads.

The project follows a layered architecture consisting of **Controllers**, **Services**, and **Repositories**, ensuring clean code, scalability, and maintainability. Authentication and authorization are implemented using **Spring Security** and **JWT**, while data persistence is handled using **Spring Data JPA** and **MySQL**.

This project was developed to strengthen backend development skills and demonstrate real-world Spring Boot application development practices.

---

#  Features

##  Authentication
- User Registration
- User Login
- JWT Authentication
- Role-Based Authorization (ADMIN & USER)

##  Product Management
- Add Product
- Update Product
- Delete Product
- View Products
- Upload Product Images

##  Category Management
- Add Category
- Update Category
- Delete Category
- View Categories

##  Shopping Cart
- Add Products to Cart
- Update Quantity
- Remove Cart Items
- Clear Cart

##  Order Management
- Place Orders
- View Order History
- View Order Details

##  Additional Features
- Global Exception Handling
- Request Validation
- Image Upload
- Swagger API Documentation
- RESTful API Design

---

#  Technology Stack

| Category | Technology |
|-----------|------------|
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security, JWT |
| ORM | Spring Data JPA, Hibernate |
| Database | MySQL |
| Build Tool | Maven |
| API Documentation | Swagger / OpenAPI |
| API Testing | Postman |
| Version Control | Git & GitHub |

---

#  System Architecture

The application follows a layered architecture to ensure scalability, maintainability, and separation of concerns.

<p align="center">
    <img src="docs/system-architecture.png" width="900"/>
</p>

---

#  Project Structure

```text
src
├── controller
├── service
│   └── impl
├── repository
├── entity
├── request
├── response
├── security
│   ├── config
│   ├── jwt
│   └── user
├── exception
├── config
└── SmartBazarApplication.java
```

---

#  Database Design

The application uses **MySQL** as the relational database.

### Main Entities

- User
- Role
- Product
- Category
- Image
- Cart
- Cart Item
- Order
- Order Item

<p align="center">
    <img src="docs/er-diagram.png" width="900"/>
</p>

---

#  JWT Authentication Flow

The application uses **JWT (JSON Web Token)** for stateless authentication.

<p align="center">
    <img src="docs/jwt-authentication.png" width="900"/>
</p>

---

#  API Documentation

The project includes interactive API documentation using **Swagger OpenAPI**.

After starting the application, access Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

<p align="center">
    <img src="docs/swagger-ui.png" width="900"/>
</p>

---

#  Getting Started

## Clone Repository

```bash
git clone https://github.com/NatarajanRaja2005/smart-bazar-backend.git
```

## Navigate to Project

```bash
cd smart-bazar-backend
```

## Configure Database

Update your `application.properties` file.

```properties
spring.datasource.url=YOUR_DATABASE_URL
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD

jwt.secret=YOUR_SECRET_KEY
```

## Build the Project

```bash
mvn clean install
```

## Run the Application

```bash
mvn spring-boot:run
```

Application starts at:

```
http://localhost:8080
```

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

#  API Testing

All REST APIs have been tested using **Postman**.

The project also includes **Swagger UI** for interactive API testing.

---

#  Future Enhancements

- Product Search
- Wishlist
- Coupon System
- Payment Gateway Integration
- Email Notifications
- Product Reviews & Ratings
- Inventory Management
- Docker Support
- Redis Caching
- CI/CD Pipeline
- Microservices Architecture

---

#  Author

**Natarajan R**

Aspiring Java Backend Developer

- GitHub: https://github.com/NatarajanRaja2005
- LinkedIn: https://www.linkedin.com/in/natarajanraja2005/
- Portfolio: https://natarajanraja2005.github.io/Natarajanportfolio/

---

#  License

This project is licensed under the **MIT License**.

---

##  If you found this project useful, consider giving it a Star on GitHub.


    <img src="docs/swagger-ui.png" width="900"/>
</p>
