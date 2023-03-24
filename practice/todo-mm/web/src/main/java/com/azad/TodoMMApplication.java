package com.azad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.azad"})
@EntityScan(basePackages = {"com.azad"})
@EnableJpaRepositories(basePackages = {"com.azad"})
public class TodoMMApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoMMApplication.class);
    }
}
