package com.example.springbatch5demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBatch5DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatch5DemoApplication.class, args);
    }
}
