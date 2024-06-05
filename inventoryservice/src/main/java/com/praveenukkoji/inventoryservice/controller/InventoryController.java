package com.praveenukkoji.inventoryservice.controller;

import com.praveenukkoji.inventoryservice.dto.QuantityAddRequest;
import com.praveenukkoji.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/inventory")
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

    @DeleteMapping(path = "/delete/{product_id}")
    public ResponseEntity<Boolean> deleteInventory(@PathVariable UUID product_id) {
        return ResponseEntity.status(200).body(inventoryService.deleteInventory(product_id));
    }
}
