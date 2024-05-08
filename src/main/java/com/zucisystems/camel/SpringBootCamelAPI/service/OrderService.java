package com.zucisystems.camel.SpringBootCamelAPI.service;

import com.zucisystems.camel.SpringBootCamelAPI.dto.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private List<Order> orders=new ArrayList<>();
    @PostConstruct
    public void initDB(){
        orders.add(new Order(1L,"order1",100.0));
        orders.add(new Order(2L,"order2",200.0));
        orders.add(new Order(3L,"order3",300.0));
        orders.add(new Order(4L,"order4",400.0));
        orders.add(new Order(5L,"order5",500.0));
    }

    public Order addOrder(Order order){
        orders.add(order);
        return order;
    }

    public List<Order> getOrders(){
        return orders;
    }

    public Order getOrder(Long id){
        for(Order order:orders){
            if(order.getId().equals(id)){
                return order;
            }
        }
        return null;
    }

}
