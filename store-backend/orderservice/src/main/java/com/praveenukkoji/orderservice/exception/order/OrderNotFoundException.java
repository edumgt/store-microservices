package com.praveenukkoji.orderservice.exception.order;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException() {
        super("unable to find order");
    }
}
