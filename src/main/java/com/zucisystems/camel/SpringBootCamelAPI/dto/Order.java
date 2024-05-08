package com.zucisystems.camel.SpringBootCamelAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class Order {
    private Long id;
    private String name;
    private double price;
}
