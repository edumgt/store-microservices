package com.praveenukkoji.orderservice.constant;

public final class Constant {

    private static final String PRODUCT_HOSTNAME = "productservice";

    private static final String API_VERSION = "v1";

    public static final String API_GATEWAY_PRODUCT_SERVICE_URL =
            "http://" + PRODUCT_HOSTNAME + ":8222/api/" + API_VERSION + "/products";

    public static final String PRODUCT_SERVICE_URL =
            "http://" + PRODUCT_HOSTNAME + ":8001/api/" + API_VERSION + "/products";

    // Private constructor to prevent instantiation
    private Constant() {
    }
}
