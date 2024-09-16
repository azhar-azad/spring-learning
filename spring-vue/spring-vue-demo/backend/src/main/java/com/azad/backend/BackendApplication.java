package com.azad.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.function.Function;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Autowired
	private DemoRepository repository;

	Function<String, Demo> demoCreator = (title) -> {
		Demo demo = new Demo();
		demo.setTitle(title);
		return repository.save(demo);
	};

	@Override
	public void run(String... args) throws Exception {
		if (repository.findAll().isEmpty()) {
			demoCreator.apply("Title 1");
			demoCreator.apply("Title 2");
			demoCreator.apply("Title 3");
		}
	}
}
