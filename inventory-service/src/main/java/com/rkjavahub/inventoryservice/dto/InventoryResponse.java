package com.rkjavahub.inventoryservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock = false;
}
