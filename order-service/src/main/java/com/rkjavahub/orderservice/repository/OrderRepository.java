package com.rkjavahub.orderservice.repository;

import com.rkjavahub.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
