package com.example.webflux;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebfluxExampleApplication {

    public static void main(String[] args) {
//        BlockHound.install();

        SpringApplication.run(WebfluxExampleApplication.class, args);
    }


    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
