package com.example.finanzas_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.finanzas_back.model")
public class FinanzasBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanzasBackApplication.class, args);
    }

}