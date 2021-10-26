package com.emily.emilyservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.emily")
@MapperScan("com.emily.emilyservice.mappers")
public class EmilyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmilyServiceApplication.class, args);
    }

}
