package com.rkjavahub.productservice;


import com.rkjavahub.productservice.dto.ProductRequest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    static {
        mongoDBContainer.start();
    }

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Should create new product")
    @Order(value = 1)
    void shouldCreateProduct() throws Exception {
        String requestBody = """
                {
                        "name": "Apple iphone 14",
                        "description": "Mobile Phone",
                        "price": 150000
                }
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Apple iphone 14"))
                .body("description", Matchers.equalTo("Mobile Phone"))
                .body("price", Matchers.equalTo(150000));

    }

/*    @Test
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
    }*/
}
