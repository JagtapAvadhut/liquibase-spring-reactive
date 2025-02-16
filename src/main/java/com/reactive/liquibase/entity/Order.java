package com.reactive.liquibase.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    private Date orderDate;
    private BigDecimal totalPrice;
    private Customer customer;
}
