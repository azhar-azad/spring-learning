package com.azad.budget;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.stream.Collectors;

@SpringBootApplication
public class BudgetApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BudgetApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Builder builder = new Builder();

		Category category = new Category();
	}
}
