package com.azad.spring_demo_docs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDemoDocsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoDocsApplication.class, args);
	}

	@Autowired
	DemoEntityRepository demoEntityRepository;

	@Override
	public void run(String... args) throws Exception {
		demoEntityRepository.save(new DemoEntity(null, "Demo Entity 1"));
		demoEntityRepository.save(new DemoEntity(null, "Demo Entity 2"));
	}
}
