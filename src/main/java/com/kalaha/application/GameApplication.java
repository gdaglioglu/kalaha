package com.kalaha.application;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@EnableVaadin({"com.kalaha.client.view"})
@ComponentScan("com.kalaha")
@SpringBootApplication
public class GameApplication {

    public static void main(String[] args) {SpringApplication.run(GameApplication.class, args);}

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}