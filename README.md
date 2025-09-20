# 🏗️ E-Commerce Microservices Architecture

This project demonstrates a **Microservices-based E-commerce System** with:

- **API Gateway** for routing and filtering
- **Service Registry (Eureka)** for service discovery
- **Centralized Identity Provider (IDP)** for JWT-based authentication
- Multiple **microservices** (User, Product, Cart, Order, Payment) each with its own database

---

## 📌 Architecture Overview

<p align="center">
  <img src="https://raw.githubusercontent.com/gsajal19/Ecommerce-Microservice/refs/heads/main/Screenshot%202025-09-20%20155016.png" alt="Microservices Architecture" width="600">
</p>
**Flow Explanation:**

1. **Client** sends a request to **API Gateway**
2. API Gateway sends token to **IDP Service** for validation
3. If valid, API Gateway fetches service location from **Eureka Service Registry**
4. Gateway forwards the request to the correct microservice
5. Microservice communicates with its own database

---

## 🛠️ Tech Stack

- **Spring Boot** (Microservices, API Gateway, Eureka Server, IDP)
- **Spring Cloud Gateway** (Routing + Token Validation)
- **Spring Security + JWT** (Authentication)
- **Eureka Server** (Service Discovery)
- **MySQL & PostgreSQL** (Per-service databases)
- **RestTemplate / WebClient** (Inter-service communication)

---

## Services Endpoints

### Centralized Identity Provider Service

```bash
http://localhost:5002/auth/login
```

