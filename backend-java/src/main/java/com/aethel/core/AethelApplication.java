package com.aethel.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AethelApplication {
    public static void main(String[] args) {
        // This line starts the entire embedded web server (Tomcat) on port 8080
        SpringApplication.run(AethelApplication.class, args);
    }
}