package com.cx.ast.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AstInterceptorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AstInterceptorApplication.class, args);
    }

}
