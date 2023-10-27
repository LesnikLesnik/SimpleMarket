package com.example.SimpleMarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.SimpleMarket")
public class SimpleMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleMarketApplication.class, args);
    }

}
