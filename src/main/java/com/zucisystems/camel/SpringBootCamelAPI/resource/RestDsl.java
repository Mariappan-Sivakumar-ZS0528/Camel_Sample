package com.zucisystems.camel.SpringBootCamelAPI.resource;

import com.zucisystems.camel.SpringBootCamelAPI.dto.Order;
import com.zucisystems.camel.SpringBootCamelAPI.service.OrderService;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class RestDsl extends RouteBuilder {

    @Autowired
    private OrderService orderService;
    @Override
    public void configure() throws Exception {
        rest().consumes(MediaType.APPLICATION_JSON_VALUE).produces(MediaType.APPLICATION_JSON_VALUE)
                .get("test/order/{orderId}")
                .outType(Order.class)
                .to("direct:order");
        from("direct:order").process(this::getOrderDetails);
            rest().post("/addOrders")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(Order.class).routeId("addOrder")
                .outType(Order.class).to("direct:addOrder");
        from("direct:addOrder").process(this::addOrderDetails);
    }
    private void getOrderDetails(Exchange exchange) {
        System.out.println("Inside getOrderDetails");
        Long orderId = exchange.getMessage().getHeader("orderId", Long.class);

        Order order = orderService.getOrder(orderId);

        Message message = new DefaultMessage(exchange.getContext());
        message.setBody(order);
        exchange.setMessage(message);
    }

    private void addOrderDetails(Exchange exchange) {
        System.out.println("Inside addOrderDetails");
//         exchange.getMessage().getBody(Order.class);
        Order order = exchange.getIn().getBody(Order.class);
        orderService.addOrder(order);
    }
}
