package com.zucisystems.camel.SpringBootCamelAPI.controller;

import com.zucisystems.camel.SpringBootCamelAPI.dto.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping("/handleOrder")
    public Order handleOrder(@RequestBody Order order){
        return order;
    }
}
