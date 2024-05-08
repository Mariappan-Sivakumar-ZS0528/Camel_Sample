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
        orders.add(new Order(1,"order1",100.0));
        orders.add(new Order(2,"order2",200.0));
        orders.add(new Order(3,"order3",300.0));
        orders.add(new Order(4,"order4",400.0));
        orders.add(new Order(5,"order5",500.0));
    }

    public Order addOrder(Order inputOrder){
        for(Order order:orders)
        {
            if(inputOrder.getId().equals(order.getId()))
            {
                return null;
            }
        }
        orders.add(inputOrder);
        return inputOrder;
    }

    public boolean deleteOrder(int id)
    {
       return orders.removeIf((odd)->odd.getId().equals(id));
    }
    public Order updateOrder(Order inputOrder)
    {
        for(Order order:orders){
            if(inputOrder.getId().equals(order.getId()))
            {
                order.setName(inputOrder.getName());
                order.setPrice(inputOrder.getPrice());
                return order;
            }
        }
        return null;

    }

    public List<Order> getOrders(){
//        try{
//            System.out.println("Test");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return orders;
    }

    public Order getOrder(Integer id){
        for(Order order:orders){
            if(order.getId().equals(id)){
                return order;
            }
        }
        return null;
    }

}
