package com.pharmacare.billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PharmaBillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmaBillingServiceApplication.class, args);
    }
}
