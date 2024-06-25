package com.praveenukkoji.productservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "inventory-feign-client", url = "http://localhost:8222/api/inventory")
public interface InventoryClient {
    @GetMapping(path = "/get")
    public ResponseEntity<Map<UUID, Integer>> getQty(@RequestParam List<UUID> product_id);
}
