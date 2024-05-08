package com.zucisystems.camel.SpringBootCamelAPI.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class TestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        System.out.println("Inside TestProcessor");
    }
}
