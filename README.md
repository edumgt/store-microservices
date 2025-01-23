# Store Microservices

A scalable e-commerce backend developed using Spring Boot with microservices architecture. This project is designed to handle various aspects of an e-commerce platform, including user management, product catalog, order processing, notifications, and payment handling. The system employs best practices for service discovery, communication, security, and deployment.

---

## Microservices

    Server's :
        1. eureka-server          (port : 8761)
        2. config-server          (port : 8888)
        3. gatewayservice         (port : 8222)

    Service's :
        1. userservice            (port : 8000)
        2. productservice         (port : 8001)
        3. orderservice           (port : 8002)
        4. paymentservice         (port : 8003)
        5. notificationservice    (port : 8004)

## API Overview

| Service                  | Base URL                                              | Description                              |
| ------------------------ |-------------------------------------------------------|------------------------------------------|
| **User Service**         | `/api/v1/roles`, `/api/v1/users`, `/api/v1/addresses` | (Roles, Users, Addresses) management.    |
| **Product Service**      | `/api/v1/products`, `/api/v1/categories`              | Product category, catalog and inventory. |
| **Order Service**        | `/api/v1/orders`                                      | Order processing.                        |
| **Payment Service**      | `/api/v1/payments`                                    | Payment processing.                      |

## Key Features

- **Microservices Architecture**:

  - **User Service**: Manage user registration, authentication, and profile.
  - **Product Service**: Handle product catalog and inventory.
  - **Order Service**: Process customer orders and manage order lifecycle.
  - **Notification Service**: Send email alerts based on system events.
  - **Payment Service**: Integrate and handle payment transactions.

- **Service Discovery and Routing**:

  - Configured **Eureka Server** for dynamic service discovery.
  - Set up **API Gateway** to route client requests to appropriate services.

- **Centralized Configuration**:

  - Leveraged **Spring Cloud Config Server** to manage service configurations centrally.

- **Asynchronous Communication**:

  - Integrated **Apache Kafka** for inter-service communication.
  - Implemented **OrderEvent** and **PaymentEvent** to enable a responsive and event-driven architecture.

- **Secure APIs**:

  - Secured all APIs with **Keycloak** using JWT-based authentication.
  - Ensured **role-based access control** through the API Gateway.

- **Containerization**:
  - Dockerized all microservices for consistent environments and streamlined deployment.

---

## Technologies Used

- **Languages**: Java
- **Frameworks**: Spring Boot
- **Databases**: PostgreSQL
- **Authentication**: Keycloak
- **Messaging**: Apache Kafka
- **Tools**: Docker
- **Configuration**: Spring Cloud Config Server
- **Service Discovery**: Eureka Server
- **API Gateway**: Spring Cloud Gateway

---

## Architecture Overview

![Microservices Architecture Diagram](Architecture%20Diagram.png)

## API call file

- you can refer postman_file : [Store APi.postman_collection.json](Store%20APi.postman_collection.json)

---

## How to Run

### Prerequisites

1. **Install Java (JDK 21 or higher)**: [Download JDK](https://adoptium.net/)
2. **Install Docker**: [Get Docker](https://docs.docker.com/get-docker/)

### Steps to run

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/praveenukkoji/store-microservices.git
   ```
   ```bash
   cd store-microservices
   ```
2. **Running using docker**:

   ```bash
   cd docker\ compose/docker-local
   ```

   ```bash
   docker compose up -d
   ```

## License

This project is licensed under the PraveenUkkoji's Org.
