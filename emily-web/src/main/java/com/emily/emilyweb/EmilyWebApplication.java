package com.emily.emilyweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.emily")
public class EmilyWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmilyWebApplication.class, args);
    }
}
