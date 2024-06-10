package com.rkjavahub.inventoryservice.dto;

import lombok.*;

@Data
@Builder
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;
}
