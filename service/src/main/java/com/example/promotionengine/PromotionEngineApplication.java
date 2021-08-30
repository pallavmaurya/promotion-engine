package com.example.promotionengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.example", exclude = DataSourceAutoConfiguration.class)
@EnableSwagger2
public class PromotionEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromotionEngineApplication.class, args);
    }

    @Bean
    public Docket checkoutApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller")).build();
    }

}
