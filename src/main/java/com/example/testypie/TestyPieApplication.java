package com.example.testypie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TestyPieApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestyPieApplication.class, args);
    }
}
