package com.praveenukkoji.orderservice.constant;

public final class Constant {
    
    public static final String API_VERSION = "v1";

    public static final String API_GATEWAY_PRODUCT_SERVICE_URL =
            "http://localhost:8222/api/" + API_VERSION + "/products";

    public static final String PRODUCT_SERVICE_URL =
            "http://localhost:8001/api/" + API_VERSION + "/products";

    // Private constructor to prevent instantiation
    private Constant() {
    }
}
