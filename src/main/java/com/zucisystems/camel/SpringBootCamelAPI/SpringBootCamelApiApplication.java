package com.zucisystems.camel.SpringBootCamelAPI;

import org.apache.camel.spring.boot.security.CamelSSLAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCamelApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCamelApiApplication.class, args);
	}

}
