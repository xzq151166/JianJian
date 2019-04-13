package com.jianjian.jianjian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"config","controller","pojo","service","Util","test"})
public class JianjianApplication {

    public static void main(String[] args) {
        SpringApplication.run(JianjianApplication.class, args);
    }
}
