<h1>Store Microservices</h1>

<p>Simple Store API using Spring Boot (microservice).</p>

## Technologies Used

+ Java 21
+ Apache Maven
+ Spring Boot
+ PosgreSQL
+ Docker

## Features

+ .....
+ .....

## Model diagram

![Store API Models.png](Store%20API%20Models.png)

## Architecture diagram

![Architecture Diagram.png](Architecture%20Diagram.png)

## Setup Steps

+ .....
+ .....

## Microservices

    Server's :
        1. eureka-server          (port : 8761)
        2. config-server          (port : 8888)

    Service's :
        1. gatewayservice         (port : 8222)
        2. userservice            (port : 8000)
        3. productservice         (port : 8001)
        4. orderservice           (port : 8002)

## Future Things

1. add payment-service:
    + handle payment requests

2. add notification-service:
    + send notification when order placed successfully
    + using apache kafka

3. ELK Stack implementation.

## API Call's

+ you can refer postman_file : [Store APi.postman_collection.json](Store%20APi.postman_collection.json)

## License

This project is licensed under the PraveenUkkoji's Org.
