package com.praveenukkoji.inventoryservice.service;

import com.praveenukkoji.inventoryservice.dto.request.CreateQuantityRequest;
import com.praveenukkoji.inventoryservice.dto.response.InventoryResponse;
import com.praveenukkoji.inventoryservice.exception.CreateInventoryException;
import com.praveenukkoji.inventoryservice.exception.ProductNotFoundException;
import com.praveenukkoji.inventoryservice.model.Inventory;
import com.praveenukkoji.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryResponse createInventory(CreateQuantityRequest createQuantityRequest)
            throws CreateInventoryException, ProductNotFoundException {

        UUID product_id = createQuantityRequest.getProduct_id();

        Optional<Inventory> queryResult = inventoryRepository.findById(product_id);

        if (queryResult.isPresent()) {
            Inventory inventory_entity = queryResult.get();
            inventory_entity.setProduct_qty(inventory_entity.getProduct_qty() + createQuantityRequest.getProduct_qty());
            inventory_entity.setModified_by(createQuantityRequest.getModified_by());

            try {
                inventory_entity = inventoryRepository.saveAndFlush(inventory_entity);

                log.info("inventory added of product id = {}", inventory_entity.getProduct_id());

                return InventoryResponse.builder()
                        .message("inventory added of product id = " + inventory_entity.getProduct_id())
                        .build();
            } catch (Exception e) {
                throw new CreateInventoryException("unable to create inventory");
            }
        } else {
            throw new ProductNotFoundException("product not found");
        }
    }

    public Map<UUID, Integer> getInventory(List<UUID> product_id) {

        List<Inventory> queryResult = inventoryRepository.findAllById(product_id);

        Map<UUID, Integer> productWithQty = new HashMap<>();

        if (!queryResult.isEmpty()) {
            log.info("quantity fetched");

            for (Inventory inventory : queryResult) {
                productWithQty.put(inventory.getProduct_id(), inventory.getProduct_qty());
            }

            return productWithQty;
        }

        log.info("product not in inventory");

        return productWithQty;
    }

    public InventoryResponse deleteInventory(UUID productId)
            throws ProductNotFoundException {

        Optional<Inventory> queryResult = inventoryRepository.findById(productId);

        if (queryResult.isPresent()) {
            inventoryRepository.deleteById(productId);

            log.info("inventory deleted for product id = {}", productId);

            return InventoryResponse.builder()
                    .message("inventory deleted for product id = " + productId)
                    .build();
        } else {
            throw new ProductNotFoundException("product not found");
        }
    }
}
