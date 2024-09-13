package com.team12.company_product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CompanyProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyProductApplication.class, args);
    }

}
