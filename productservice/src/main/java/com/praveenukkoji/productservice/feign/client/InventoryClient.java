package com.praveenukkoji.productservice.feign.client;

import com.praveenukkoji.productservice.dto.inventory.AddQuantityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "inventory-feign-client", url = "http://localhost:8222/api/inventory")
public interface InventoryClient {

    @PostMapping(path = "/addqty")
    public ResponseEntity<Integer> addQty(@RequestBody AddQuantityRequest addQuantityRequest);

    @GetMapping(path = "/getqty")
    public ResponseEntity<Map<UUID, Integer>> getQty(@RequestParam List<UUID> product_ids);

    @DeleteMapping(path = "/deleteqty/{product_id}")
    public ResponseEntity<Boolean> deleteInventory(@PathVariable UUID product_id);
}
