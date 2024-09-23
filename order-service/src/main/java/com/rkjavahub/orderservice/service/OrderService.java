package com.rkjavahub.orderservice.service;

import com.rkjavahub.event.OrderPlacedEvent;
import com.rkjavahub.orderservice.dto.InventoryResponse;
import com.rkjavahub.orderservice.dto.OrderLineIteamsDTO;
import com.rkjavahub.orderservice.dto.OrderRequest;
import com.rkjavahub.orderservice.model.Order;
import com.rkjavahub.orderservice.model.OrderLineIteams;
import com.rkjavahub.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineIteams> orderLineIteams = orderRequest.getOrderLineIteamsDTOS().stream().map(this::mapToOrderDTO).toList();

        order.setOrderLineIteamsList(orderLineIteams);

        // Getting skucodes from orderLineIteams
        List<String> skuCodes = orderLineIteams.stream().map(OrderLineIteams::getSkuCode).toList();

        // Call inventory service and check inventory for profuct is in stock before placing an order
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get().uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()).retrieve().bodyToMono(InventoryResponse[].class).block();

        assert inventoryResponses != null;

        for (InventoryResponse inventoryResponse : inventoryResponses) {
            System.out.println("Printing inventoryResponse data");
            System.out.println("SkuCode: " + inventoryResponse.getSkuCode());
            System.out.println("IsInStock: " + inventoryResponse.isInStock());
        }

        System.out.println(inventoryResponses.length);

        //assert inventoryResponses != null;

        boolean allProductisInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (allProductisInStock) {
            orderRepository.save(order);
            System.out.println("Calling notification-service");
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
            System.out.println("notification-service call ended");
            return "Order placed successfully!";
        } else {
            throw new IllegalArgumentException("Product is not available in stock, please try again later");
        }
    }

    private OrderLineIteams mapToOrderDTO(OrderLineIteamsDTO orderLineIteamsDTO) {
        OrderLineIteams orderLineIteams = new OrderLineIteams();
        orderLineIteams.setId(orderLineIteamsDTO.getId());
        orderLineIteams.setSkuCode(orderLineIteamsDTO.getSkuCode());
        orderLineIteams.setQuantity(orderLineIteamsDTO.getQuantity());
        orderLineIteams.setPrice(orderLineIteamsDTO.getPrice());

        return orderLineIteams;
    }

}
