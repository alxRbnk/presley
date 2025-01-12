package org.rbnk.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.rbnk.api", "org.rbnk.core"})
@EnableFeignClients(basePackages = "org.rbnk.api.client")
public class ApiNewsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiNewsApplication.class, args);
    }
}