package com.zucisystems.camel.SpringBootCamelAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private Long id;
    private String name;
    private double price;
}
