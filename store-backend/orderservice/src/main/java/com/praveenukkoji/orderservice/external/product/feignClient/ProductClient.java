package com.praveenukkoji.orderservice.external.product.feignClient;

import com.praveenukkoji.orderservice.external.product.dto.request.ProductDetailRequest;
import com.praveenukkoji.orderservice.external.product.dto.response.ProductDetailResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "product-client", url = "http://productservice:8001/api/v1/products")
public interface ProductClient {

    // get product detail
    @PostMapping(value = "/product-detail")
    @Headers("Content-Type: application/json")
    ResponseEntity<List<ProductDetailResponse>> getProductDetails(
            @RequestBody List<ProductDetailRequest> productDetailRequest);

    // decrease product stock
    @PatchMapping(value = "/decrease-stock")
    ResponseEntity<UUID> decreaseStock(
            @RequestParam("productId") String productId,
            @RequestParam("decreaseStock") Integer decreaseStock);
}
