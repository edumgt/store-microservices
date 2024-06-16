package com.praveenukkoji.inventoryservice.service;

import com.praveenukkoji.inventoryservice.dto.AddQuantityRequest;
import com.praveenukkoji.inventoryservice.dto.GetQuantityRequest;
import com.praveenukkoji.inventoryservice.dto.GetQuantityResponse;
import com.praveenukkoji.inventoryservice.model.Inventory;
import com.praveenukkoji.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Integer addQty(AddQuantityRequest addQuantityRequest) {

        UUID product_id = addQuantityRequest.getProduct_id();

        Optional<Inventory> queryResult = inventoryRepository.findById(product_id);

        Inventory entity = null;

        if (queryResult.isEmpty()) {
            entity = Inventory.builder()
                    .product_id(addQuantityRequest.getProduct_id())
                    .product_qty(addQuantityRequest.getProduct_qty())
                    .created_on(LocalDate.now())
                    .created_by(addQuantityRequest.getCreated_by())
                    .build();
        } else {
            entity = queryResult.get();
            entity.setProduct_qty(entity.getProduct_qty() + addQuantityRequest.getProduct_qty());
        }

        entity = inventoryRepository.saveAndFlush(entity);
        log.info("add_qty - quantity added inventory of product id = {}", entity.getProduct_id());

        return entity.getProduct_qty();
    }

    public List<GetQuantityResponse> getQty(GetQuantityRequest getQuantityRequest) {
        List<UUID> product_ids = getQuantityRequest.getProduct_ids();

        List<Inventory> queryResult = inventoryRepository.findAllById(product_ids);

        if (!queryResult.isEmpty()) {
            log.info("get_qty - quantity fetched of product id's");

            Map<UUID, Integer> productWithQty = new HashMap<>();
            for (Inventory inv : queryResult) {
                productWithQty.put(inv.getProduct_id(), inv.getProduct_qty());
            }

            List<GetQuantityResponse> response = product_ids.stream().map(id -> GetQuantityResponse.builder()
                    .product_id(id)
                    .qty(productWithQty.getOrDefault(id, 0))
                    .build()).toList();

            return response;
        }

        log.info("get_qty - product id's are not in inventory");

        return product_ids.stream().map(id -> GetQuantityResponse.builder()
                .product_id(id)
                .qty(0)
                .build()).toList();
    }

    public Boolean deleteInventory(UUID productId) {

        Optional<Inventory> queryResult = inventoryRepository.findById(productId);

        if (queryResult.isPresent()) {
            inventoryRepository.deleteById(productId);
            log.info("delete_qty - inventory deleted for product id = {}", productId);

            return true;
        }

        log.info("delete_qty - product not in inventory");

        return false;
    }
}
