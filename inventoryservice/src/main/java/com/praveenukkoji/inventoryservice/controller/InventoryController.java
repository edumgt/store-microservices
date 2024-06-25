package com.praveenukkoji.inventoryservice.controller;

import com.praveenukkoji.inventoryservice.dto.request.CreateQuantityRequest;
import com.praveenukkoji.inventoryservice.dto.response.InventoryResponse;
import com.praveenukkoji.inventoryservice.exception.CreateInventoryException;
import com.praveenukkoji.inventoryservice.exception.ProductNotFoundException;
import com.praveenukkoji.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

// @RefreshScope only changes property values which are used in service etc. using @Value

@RestController
@RequestMapping(path = "/api/inventory")
@RefreshScope
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping(path = "/create")
    public ResponseEntity<InventoryResponse> createInventory(@RequestBody CreateQuantityRequest createQuantityRequest)
            throws CreateInventoryException, ProductNotFoundException {
        return ResponseEntity.status(201).body(inventoryService.createInventory(createQuantityRequest));
    }

    @GetMapping(path = "/get")
    public ResponseEntity<Map<UUID, Integer>> getInventory(@RequestParam List<UUID> product_id) {
        return ResponseEntity.status(200).body(inventoryService.getInventory(product_id));
    }

    @DeleteMapping(path = "/delete/{product_id}")
    public ResponseEntity<InventoryResponse> deleteInventory(@PathVariable UUID product_id)
            throws ProductNotFoundException {
        return ResponseEntity.status(200).body(inventoryService.deleteInventory(product_id));
    }
}
