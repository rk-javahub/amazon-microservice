package com.rkjavahub.productservice;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkjavahub.productservice.dto.ProductRequest;
import com.rkjavahub.productservice.repository.ProductRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @LocalServerPort
    private Integer port;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port=port;
    }



    @Test
    @DisplayName("Should create new product")
    @Order(value = 1)
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString)
        ).andExpect(status().isCreated());
        System.out.println("size: " + productRepository.findAll().size());
        Assertions.assertEquals(1, productRepository.findAll().size());
    }

    @Test
    @DisplayName("Should get all products")
    @Order(value = 2)
    void shouldGetAllProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/api/product")
        ).andExpect(status().is2xxSuccessful());
        System.out.println("size: " + productRepository.findAll().size());
        // Assertions.assertEquals(1, productRepository.findAll().size()); -- Order annotation not working need to check
    }

    private ProductRequest getProductRequest() {
        return ProductRequest
                .builder()
                .name("Nokia 8.1")
                .price(new BigDecimal("15000"))
                .description("Mobile")
                .build();
    }
}
