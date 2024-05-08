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
//
        rest()
                .get("/hello")
//                .produces(MediaType.APPLICATION_JSON_VALUE)
                .to("direct:hello");
        from("direct:hello").transform().simple("Hello World");
        from("servlet:hello1").process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                // Access HTTP headers sent by the client
                Message message = exchange.getMessage();
                String contentType = message.getHeader(Exchange.CONTENT_TYPE, String.class);
                String httpUri = message.getHeader(Exchange.HTTP_URI, String.class);

                // Set the response body
                message.setBody("<b>Got Content-Type: " + contentType  +"= , URI: " + httpUri + "</b>");
            }
        });
        from("rest://get:order/{orderId}?produces=application/json")
                .outputType(Order.class)
                .process(this::getOrderDetails);
        from("jetty:http://localhost:8081/test").log("test").process(this::test);
        from("jetty:http://localhost:8081/addOrder?httpMethodRestrict=POST")
                .outputType(Order.class)
                .inputType(Order.class)
                .log("input found").process(this::addOrder);
//        from("rest:get:order?produces=application/json")
//                .outputType(Order.class)
//                .process(this::test);
//        from("timer:test?period=1000").log("test").process(this::test).to("log:test");
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


    private void getOrderDetails(Exchange exchange) {
        System.out.println("Inside getOrderDetails");
        Long orderId = exchange.getMessage().getHeader("orderId", Long.class);

        Order order = orderService.getOrder(orderId);

        Message message = new DefaultMessage(exchange.getContext());
        message.setBody(order);
        exchange.setMessage(message);
    }
    private void test(Exchange exchange) {
        System.out.println("Inside test");
        Order order = orderService.getOrder(1L);
        Message message = new DefaultMessage(exchange.getContext());
        message.setBody(order);
        exchange.setMessage(message);
    }
}
