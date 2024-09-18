package com.team12.order_delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = "com.team12")
@EnableFeignClients
public class OrderDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderDeliveryApplication.class, args);
    }

}
