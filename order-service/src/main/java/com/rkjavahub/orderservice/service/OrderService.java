package com.rkjavahub.orderservice.service;

import com.rkjavahub.orderservice.dto.OrderLineIteamsDTO;
import com.rkjavahub.orderservice.dto.OrderRequest;
import com.rkjavahub.orderservice.model.Order;
import com.rkjavahub.orderservice.model.OrderLineIteams;
import com.rkjavahub.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineIteams> orderLineIteams = orderRequest.getOrderLineIteamsDTOS().stream().map(this::mapToOrderDTO).toList();

        order.setOrderLineIteamsList(orderLineIteams);

        // Getting skucodes from orderLineIteams
        List<String> skuCodes = orderLineIteams.stream().map(OrderLineIteams::getSkuCode).toList();

        // check inventory before placing an order
        webClient.get().uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build());


        orderRepository.save(order);
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
