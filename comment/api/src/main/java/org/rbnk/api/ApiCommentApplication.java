package org.rbnk.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.rbnk.api", "org.rbnk.core"})
public class ApiCommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiCommentApplication.class, args);
    }
}