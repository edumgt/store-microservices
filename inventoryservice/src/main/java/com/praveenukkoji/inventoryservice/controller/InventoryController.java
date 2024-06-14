package com.praveenukkoji.inventoryservice.controller;

import com.praveenukkoji.inventoryservice.dto.QuantityAddRequest;
import com.praveenukkoji.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// @RefreshScope only changes property values which are used in service etc. using @Value

@RestController
@RequestMapping(path = "/api/inventory")
@RefreshScope
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping(path = "/addqty")
    public ResponseEntity<Integer> addQty(@RequestBody QuantityAddRequest quantityAddRequest) {
        return ResponseEntity.status(201).body(inventoryService.addQty(quantityAddRequest));
    }

    @GetMapping(path = "/getqty/{product_id}")
    public ResponseEntity<Integer> getQty(@PathVariable UUID product_id) {
        return ResponseEntity.status(200).body(inventoryService.getQty(product_id));
    }

    @DeleteMapping(path = "/deleteqty/{product_id}")
    public ResponseEntity<Boolean> deleteInventory(@PathVariable UUID product_id) {
        return ResponseEntity.status(200).body(inventoryService.deleteInventory(product_id));
    }
}
