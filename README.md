<h1 align="center" id="title">Store Microservices</h1>

<p id="description">Simple Store API Microservice using Spring Boot.</p>

<h2>Features</h2>

Here're some of the project's best features:

- 1
- .....

<h2>Built with</h2>

Technologies used in the project:

- Java 21
- Apache Maven 3.9.6
- Spring Boot 3.3.0

<h2>Model diagram</h2>

![model e-r diagram](https://github.com/praveenukkoji/store-microservices/blob/main/Store%20API%20Models.png)

<h2>Architecture diagram</h2>

![architecture diagram](https://github.com/praveenukkoji/store-microservices/blob/main/Architecture%20Diagram.png)

## Microservices

    Server's :
        1. eureka-server (port : 8761)
        2. admin-server (port : 9090)
        3. config-server (port : 8888)

    Service's :
        1. gatewayservice (port : 8222)
        2. userservice (port : 8000)
        3. productservice (port : 8001)
        4. order service (port : 8002)

## Things TODO

- 1
- .....

## Future Things

1. add payment service:
    - handle payment requests

2. add notificationservice:
    - send notification when order placed successfully
    - using apache kafka

3. create update request for all services.
4. ELK Stack implementation.

## API Calls

### Product Service

```http
  POST /api/product/create
```

- creates product.

```http
  GET /api/product/get/{productId}
```

- retrieves the details for passed productId.

```http
  GET /api/product/get/all
```

- retrieves the details of all products.

```http
  DELETE /api/product/delete/{productId}
```

- deletes the product having productId same as passed productId.

### Order Service

```http
  POST /api/order/create
```

- creates order with products belonging to order.

```http
  GET /api/order/get/{orderId}
```

- retrieves the order details for passed orderId.

### User Service

```http
  POST /api/user/create
```

- creates user.

```http
  GET /api/user/get/{userId}
```

- retrieves the user details for passed userId.

```http
  DELETE /api/user/delete/{userId}
```

- deletes the user having userId same as passed userId.

<h2>License:</h2>

This project is licensed under the PraveenUkkoji Org.
