package com.praveenukkoji.inventoryservice.service;

import com.praveenukkoji.inventoryservice.dto.QuantityAddRequest;
import com.praveenukkoji.inventoryservice.model.Inventory;
import com.praveenukkoji.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Integer addQty(QuantityAddRequest quantityAddRequest) {

        UUID product_id = quantityAddRequest.getProduct_id();

        Optional<Inventory> queryResult = inventoryRepository.findById(product_id);

        Inventory entity = null;

        if (queryResult.isEmpty()) {
            entity = Inventory.builder()
                    .product_id(quantityAddRequest.getProduct_id())
                    .product_qty(quantityAddRequest.getProduct_qty())
                    .created_on(LocalDate.now())
                    .created_by(quantityAddRequest.getCreated_by())
                    .build();
        } else {
            entity = queryResult.get();
            entity.setProduct_qty(entity.getProduct_qty() + quantityAddRequest.getProduct_qty());
        }

        entity = inventoryRepository.saveAndFlush(entity);
        log.info("add_qty - quantity added inventory of product id = {}", entity.getProduct_id());

        return entity.getProduct_qty();
    }

    public Integer getQty(UUID product_id) {

        Optional<Inventory> queryResult = inventoryRepository.findById(product_id);

        if (queryResult.isPresent()) {
            log.info("get_qty - quantity fetched of product id = {}", product_id);

            return queryResult.get().getProduct_qty();
        }

        log.info("get_qty - not in inventory, product id = {}", product_id);

        return 0;
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
