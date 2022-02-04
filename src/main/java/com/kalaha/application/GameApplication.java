package com.kalaha.application;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableVaadin({"com.kalaha.client.view"})
@ComponentScan("com.kalaha")
@SpringBootApplication
public class GameApplication {

    public static void main(String[] args) {SpringApplication.run(GameApplication.class, args);}
}