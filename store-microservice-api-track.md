# Store Microservices

## Microservices

    Server's :
        1. eureka-server (port : 8761)
        2. admin-server (port : 9090)
        3. config-server (port : 8888)

    Service's :
        1. api-gateway (port : 8222)
        2. product-service (port : 8001)
        3. inventory-service (port : 8002)
        4. order-service (port : 8003)

## Things TODO

1. method to update inventory entity (qty attribute).
2. when creating order fetch product prices from product service.
3. when creating order check qty of that product if available then only create or cancel whole order.

## Future Things

1. add Notification-Service : (using email)

   - send notification when,
     1. when order created successfully.
     2.

2. add Identity-Service : for authentication and authorization.
3. create update request for all services.

## API Calls

### Product Service

```http
  POST /api/product/create
```

- creates product and also adds product in inventory with 0 quantity.

```http
  GET /api/product/get/{id}
```

- reterives the details of passed product id with qty from inventory.

```http
  GET /api/product/get/all
```

- reterives the details of all products with qty from inventory.

```http
  DELETE /api/product/delete/{id}
```

- deletes the product having id same as passed id.

### Inventory Service

```http
  POST /api/inventory/addqty
```

- creates inventory entity with product id and qty passed and if already qty available the add new_qty to it.

```http
  GET /api/inventory/getqty
```

- reterives the qty of passed product id's.

```http
  DELETE /api/inventory/delete/{id}
```

- deletes the inventory having product id same as passed id.

### Order Service

```http
  POST /api/order/create
```

- creates order entity with products belonging to order.

```http
  GET /api/order/get/{id}
```

- reterives the order details of passed order id.
