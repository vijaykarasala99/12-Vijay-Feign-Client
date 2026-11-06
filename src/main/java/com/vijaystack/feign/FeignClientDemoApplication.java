package com.vijaystack.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.vijaystack.feign.client")
public class FeignClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignClientDemoApplication.class, args);
    }
}
