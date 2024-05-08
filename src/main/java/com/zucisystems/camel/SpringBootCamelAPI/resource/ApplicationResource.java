package com.zucisystems.camel.SpringBootCamelAPI.resource;

import com.zucisystems.camel.SpringBootCamelAPI.dto.Order;
import com.zucisystems.camel.SpringBootCamelAPI.service.OrderService;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingDefinition;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.support.DefaultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationResource extends RouteBuilder {

    @Autowired
    private OrderService orderService;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("jetty")
                .port(8081)
                .host("localhost")
                .bindingMode(RestBindingMode.json);
        RestBindingDefinition restBindingDefinition = new RestBindingDefinition();
        restBindingDefinition.setBindingMode(MediaType.APPLICATION_JSON_VALUE);

        rest()
                .get("/hello")
                .to("direct:hello");
        from("direct:hello").transform().simple("Hello World");
        from("servlet:hello1").process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                Message message = exchange.getMessage();
                String contentType = message.getHeader(Exchange.CONTENT_TYPE, String.class);
                String httpUri = message.getHeader(Exchange.HTTP_URI, String.class);

                // Set the response body
                message.setBody("<b>Got Content-Type: " + contentType  +"= , URI: " + httpUri + "</b>");
            }
        });
//        from("rest://get:order/{orderId}?produces=application/json")
//                .outputType(Order.class)
//                .process(this::getOrderDetails);
        from("jetty:http://localhost:8081/order/{orderId}").process(this::getOrderDetails).marshal().json();
        from("jetty:http://localhost:8081/addOrder?httpMethodRestrict=POST")
                .unmarshal().json(Order.class)
                .log("input found").process(this::addOrder).marshal().json();
        from("jetty:http://localhost:8081/getOrders").process(this::getOrders).marshal().json();
        from("jetty:http://localhost:8081/updateOrder?httpMethodRestrist=PUT")
                .unmarshal().json(Order.class)
                .process(this::updateOrder).marshal().json();
        from("jetty:http://localhost:8081/deleteOrder/{orderId}").process(this::deleteOrder);

    }

    private void addOrder(Exchange exchange) {
        System.out.println("Inside addOrder");
        Order order = exchange.getIn().getBody(Order.class);
        System.out.println(order);
        Order order1=orderService.addOrder(order);
        System.out.println(orderService.getOrders());
        Message message = new DefaultMessage(exchange.getContext());
        message.setBody(order1);
        exchange.setMessage(message);

    }
     private void getOrders(Exchange exchange){
         List<Order> orders= orderService.getOrders();
         Message message=new DefaultMessage(exchange.getContext());
         message.setBody(orders);
         exchange.setMessage(message);
     }

     private void updateOrder(Exchange exchange)
     {
        Order order=exchange.getIn().getBody(Order.class);
        Order order1=orderService.updateOrder(order);
        Message message=new DefaultMessage(exchange.getContext());
        message.setBody(order1);
        exchange.setMessage(message);
     }

    private void getOrderDetails(Exchange exchange) {
        System.out.println("Inside getOrderDetails");
        System.out.println(exchange.getMessage().getHeaders());
        String camelHttpPath = exchange.getMessage().getHeader("CamelHttpPath",String.class);
        String[] parts = camelHttpPath.split("/");
        String orderIdString = parts[parts.length - 1]; // Get the last part of the path
        int orderId = Integer.parseInt(orderIdString); // Convert the orderId to an integer

        Order order = orderService.getOrder(orderId);

        Message message = new DefaultMessage(exchange.getContext());
        message.setBody(order);
        exchange.setMessage(message);
    }

    private void deleteOrder(Exchange exchange){
        String camelHttpPath = exchange.getMessage().getHeader("CamelHttpPath", String.class);
        String[] parts = camelHttpPath.split("/");
        String orderIdString = parts[parts.length - 1]; // Get the last part of the path
        int orderId = Integer.parseInt(orderIdString); // Convert the orderId to an integer

        boolean status = orderService.deleteOrder(orderId); // Assuming deleteOrder method takes orderId as parameter

        Message message = new DefaultMessage(exchange.getContext());
        if (status) {
            message.setBody("Order ID " + orderId + " has been successfully deleted.");
        } else {
            message.setBody("Error found with deleting the Order");
        }

        exchange.setMessage(message);
    }

}
