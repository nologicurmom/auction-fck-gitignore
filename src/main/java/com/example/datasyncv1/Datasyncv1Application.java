package com.example.datasyncv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.datasyncv1.controllerAPI")
@ComponentScan(basePackages = "com.example.datasyncv1.controllerVIEW")

public class Datasyncv1Application {

    public static void main(String[] args) {
        SpringApplication.run(Datasyncv1Application.class, args);
    }

}
