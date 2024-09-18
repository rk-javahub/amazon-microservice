package com.rkjavahub.inventoryservice.service;

import com.rkjavahub.inventoryservice.dto.InventoryResponse;
import com.rkjavahub.inventoryservice.model.Inventory;
import com.rkjavahub.inventoryservice.repository.InventoryRepository;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Data
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        // Code added for checking timeout in resilience4j
      /*  log.info("Wait started");
        Thread.sleep(10000);
        log.info("Wait finished");*/


        List<Inventory> inventories = inventoryRepository.findBySkuCodeIn(skuCode);

        System.out.println("Inventory size:" + inventories.size());
        for (Inventory inventory : inventories) {
            System.out.println(inventory.getSkuCode());
            System.out.println(inventory.getQuantity());
        }


        return inventories.stream().map(inventory -> InventoryResponse.builder().skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0).build()).toList();
    }
}
