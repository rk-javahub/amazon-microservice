package com.rkjavahub.inventoryservice.service;

import com.rkjavahub.inventoryservice.dto.InventoryResponse;
import com.rkjavahub.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(I -> InventoryResponse
                .builder()
                .skuCode(I.getSkuCode())
                .isInStock(I.getQuantity() > 0)
                .build())
                .toList();
    }
}
