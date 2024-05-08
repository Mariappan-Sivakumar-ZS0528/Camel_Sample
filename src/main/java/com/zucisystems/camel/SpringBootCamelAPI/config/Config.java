package com.zucisystems.camel.SpringBootCamelAPI.config;

import jakarta.servlet.Servlet;
import org.apache.camel.spi.RestConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
//    @Bean
//    public ServletRegistrationBean<Servlet> servletRegistrationBean() {
//        ServletRegistrationBean<Servlet> registration = new ServletRegistrationBean<>(new CamelHttpTransportServlet(), "/camel/*");
//        registration.setName("CamelServlet");
//        return registration;
//    }
@Bean
public RestConfiguration configureRest() {
    RestConfiguration restConfiguration = new RestConfiguration();
    // Configure the rest configuration here
    restConfiguration.setComponent("servlet"); // or any other appropriate component
    return restConfiguration;
}
}
