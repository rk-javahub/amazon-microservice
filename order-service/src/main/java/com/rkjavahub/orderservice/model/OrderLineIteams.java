package com.rkjavahub.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "t_order_line_iteams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineIteams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
