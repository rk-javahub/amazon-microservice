package com.rkjavahub.productservice.controller;

import com.rkjavahub.productservice.dto.ProductRequest;
import com.rkjavahub.productservice.dto.ProductResponse;
import com.rkjavahub.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @LoadBalanced
    private List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

}
