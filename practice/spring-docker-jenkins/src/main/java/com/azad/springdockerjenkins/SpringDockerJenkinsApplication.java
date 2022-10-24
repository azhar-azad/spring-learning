package com.azad.springdockerjenkins;

import com.azad.springdockerjenkins.entity.ThemeParkRide;
import com.azad.springdockerjenkins.repository.ThemeParkRideRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.context.Theme;

@SpringBootApplication
public class SpringDockerJenkinsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDockerJenkinsApplication.class, args);
	}

	@Bean
	public CommandLineRunner sampleData(ThemeParkRideRepository repository) {
		return (args) -> {
			repository.save(new ThemeParkRide("Roller coaster", "Train ride that speeds you along", 5, 3));
			repository.save(new ThemeParkRide("Log flume", "Boat ride with plenty of splashes", 3, 2));
			repository.save(new ThemeParkRide("Teacups", "Spinning ride in a giant tea-cup", 2, 4));
		};
	}

}
