package com.azad.demo_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.azad.demo_backend")
public class DemoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoBackendApplication.class, args);
	}

}
