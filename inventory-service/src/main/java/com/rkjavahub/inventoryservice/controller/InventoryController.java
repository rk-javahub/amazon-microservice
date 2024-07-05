package com.rkjavahub.inventoryservice.controller;

import com.rkjavahub.inventoryservice.dto.InventoryResponse;
import com.rkjavahub.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        List<InventoryResponse> inventoryResponseList=inventoryService.isInStock(skuCode);

        for (InventoryResponse inventoryResponse : inventoryResponseList) {
            System.out.println("Printing inventoryResponse data");
            System.out.println("SkuCode: " + inventoryResponse.getSkuCode());
            System.out.println("IsInStock: " + inventoryResponse.isInStock());
        }


        return inventoryResponseList;
    }
}
