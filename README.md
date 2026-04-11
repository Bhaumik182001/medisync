# MediSync Identity Service
[![MediSync CI](https://github.com/Bhaumik182001/medisync/actions/workflows/ci.yml/badge.svg)](https://github.com/Bhaumik182001/medisync/actions)

The **Gatekeeper Identity Service** is the central authentication and authorization microservice for the MediSync healthcare ecosystem. It provides secure, stateless user management, issuing cryptographically signed JSON Web Tokens (JWT) to authenticate requests across the distributed architecture.

## 🛠️ Tech Stack
* **Language:** Java 21
* **Framework:** Spring Boot 4.0.4
* **Security:** Spring Security & JJWT (0.12.5)
* **Database:** MongoDB (Spring Data MongoDB)
* **Build Tool:** Maven
* **CI/CD:** GitHub Actions

## 🏗️ Core Architecture & Security
* **Stateless Authentication:** Utilizes `OncePerRequestFilter` to intercept requests, validate Bearer tokens, and establish a stateless Security Context without relying on server-side sessions.
* **Dynamic Role Management:** Supports dynamic Role-Based Access Control (RBAC). Roles (e.g., `PATIENT`, `PROVIDER`) are embedded directly into the JWT payload as custom claims.
* **Hardened Database Connections:** Implements a custom `MongoClientSettings` configuration to explicitly manage authentication sources and prevent connection string parsing vulnerabilities.
* **Cross-Origin Resource Sharing:** CORS configured to strictly accept pre-flight and standard requests from the Vite React frontend (`http://localhost:5173`) with full credential support.

## 📡 API Endpoints

All endpoints are prefixed with `/api/v1`.

| HTTP Method | Endpoint | Auth Required | Description |
| :--- | :--- | :---: | :--- |
| `POST` | `/auth/register` | ❌ | Registers a new user (Patient/Provider) and returns a JWT. |
| `POST` | `/auth/login` | ❌ | Authenticates credentials and returns a JWT. |
| `GET` | `/patients/me` | ✅ (Bearer) | Retrieves the profile data of the currently authenticated user. |

## 🚀 Getting Started

### Prerequisites
* **Java 21** installed locally.
* **Docker & Docker Compose** (to run the MongoDB instance).

### 1. Start the Infrastructure
The identity service requires MongoDB. Spin up the database using the provided docker-compose file:
```bash
docker-compose up -d mongodb
```

### 2. Run the Application
You can run the application using the Maven wrapper:
```bash
./mvnw spring-boot:run
```
*The service will start on `http://localhost:8081`.*

### Environment Variables
The application relies on the following configurations mapped in `application.yml`:

```yaml
server:
  port: 8081
spring:
  data:
    mongodb:
      host: localhost
      port: 27017
      database: medisync_identity
      username: rootadmin
      password: rootpassword
application:
  security:
    jwt:
      secret-key: [YOUR_BASE64_ENCODED_SECRET]
      expiration: 86400000 # 24 hours in milliseconds
```

## 🧪 Testing
Run the automated test suite, which includes full MVC integration tests for the authentication controllers:
```bash
./mvnw test
```