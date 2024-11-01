package com.praveenukkoji.orderservice.external.product.feignClient;

import com.praveenukkoji.orderservice.external.product.dto.request.DecreaseProductStockRequest;
import com.praveenukkoji.orderservice.external.product.dto.request.ProductDetailRequest;
import com.praveenukkoji.orderservice.external.product.dto.response.ProductDetailResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "product-service", url = "${feign.client.config.productservice.url}")
public interface ProductClient {

    // get product detail
    @PostMapping(value = "/product-detail")
    @Headers("Content-Type: application/json")
    ResponseEntity<List<ProductDetailResponse>> getProductDetails(
            @RequestBody List<ProductDetailRequest> productDetailRequest);

    // decrease product stock
    @PatchMapping(value = "/decrease-stock")
    ResponseEntity<Boolean> decreaseStock(@RequestBody List<DecreaseProductStockRequest> decreaseProductStockRequest);
}
