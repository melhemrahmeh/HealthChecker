# HealthChecker

A Spring Boot backend application for monitoring multiple types of services (HTTP, TCP, Database) with automated health checks and REST APIs.

**Package:** `com.melhem.healthchecker`

## Project Overview

HealthChecker allows DevOps engineers and backend developers to:

* Add and manage multiple monitored services
* Periodically check their health status
* Store and retrieve recent health results
* Expose all data via REST APIs
* Use Swagger UI for API exploration

It uses DTOs to avoid JSON serialization issues with lazy-loaded JPA entities.

## Features

* Monitor HTTP/HTTPS endpoints, TCP ports, and Databases (via JDBC)
* Automated periodic health checks using Spring `@Scheduled`
* REST API for CRUD operations on monitored services
* Retrieve last 10 health check statuses per service
* Swagger UI for interactive API testing
* H2 in-memory database for fast development

## Technologies

* Java 24
* Spring Boot 3.5.5
* Spring Data JPA / Hibernate
* H2 Database
* Springdoc OpenAPI (Swagger UI)
* Lombok (optional)
* Maven

## Getting Started

### Prerequisites

* Java 24+
* Maven
* IDE (IntelliJ IDEA recommended)

### Installation

1. Clone the repository:

```bash
git clone <repository-url>
cd healthchecker
```

2. Build the project:

```bash
mvn clean install
```

3. Run the application:

```bash
mvn spring-boot:run
```

* Server runs on **[http://localhost:8080](http://localhost:8080)**

### H2 Console

* Access at: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* JDBC URL: `jdbc:h2:mem:healthdb`
* User: `sa`
* Password: *(leave empty)*

## API Endpoints

Swagger UI available at: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

| Method | Endpoint                  | Description                 |
| ------ | ------------------------- | --------------------------- |
| GET    | /api/services             | List all monitored services |
| POST   | /api/services             | Add a new service           |
| DELETE | /api/services/{id}        | Delete a service            |
| GET    | /api/services/{id}/status | Get last 10 health checks   |

### Example: Add Service

```json
{
  "name": "Google",
  "type": "HTTP",
  "address": "https://www.google.com",
  "port": null
}
```

### Example: Get Status

```json
[
  {
    "healthy": true,
    "message": "HTTP status: 200 OK",
    "checkedAt": "2025-08-28T12:34:56"
  }
]
```

## Scheduling & Health Checks

* Runs every 60 seconds
* Ensure `@EnableScheduling` is present in `HealthCheckerApplication.java`
* Optional: trigger health checks immediately on startup

## Testing

* Repository / DAO tests with `@DataJpaTest`
* Service layer tests using JUnit 5
* Controller / API integration tests with `@SpringBootTest`

## Future Improvements

* Email alerts for failing services
* Slack / Teams notifications
* Persistent production database
* Customizable check intervals per service
* UI dashboard for monitoring

## Author

**Melhem Rahmeh** – DevOps Engineer / Backend Developer
