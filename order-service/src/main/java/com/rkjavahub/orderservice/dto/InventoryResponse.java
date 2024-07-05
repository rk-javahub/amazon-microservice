package com.rkjavahub.orderservice.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;
}
