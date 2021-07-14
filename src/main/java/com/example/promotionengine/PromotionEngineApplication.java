package com.example.promotionengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.example", exclude = DataSourceAutoConfiguration.class)
public class PromotionEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromotionEngineApplication.class, args);
    }

}
