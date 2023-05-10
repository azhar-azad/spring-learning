package com.azad.basicecommerce;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.azad.basicecommerce"})
@EntityScan(basePackages = {"com.azad.basicecommerce"})
@EnableJpaRepositories(basePackages = {"com.azad.basicecommerce"})
public class BasicEcommerceMMApplication {
    public static void main( String[] args ) {
        SpringApplication.run(BasicEcommerceMMApplication.class);
    }
}
