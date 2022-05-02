package com.azad.userservice;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.azad.userservice.entities.Role;
import com.azad.userservice.entities.User;
import com.azad.userservice.services.UserService;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.createRole(new Role(null, "ROLE_USER"));
			userService.createRole(new Role(null, "ROLE_MANAGER"));
			userService.createRole(new Role(null, "ROLE_ADMIN"));
			userService.createRole(new Role(null, "ROLE_SUPER_ADMIN"));
			
			userService.createUser(new User(null, "John Doe", "john", "1234", new ArrayList<>()));
			userService.createUser(new User(null, "Jane Doe", "jane", "1234", new ArrayList<>()));
			userService.createUser(new User(null, "John Smith", "smith", "1234", new ArrayList<>()));
			
			userService.addRoleToUser("john", "ROLE_USER");
			userService.addRoleToUser("john", "ROLE_MANAGER");
			userService.addRoleToUser("jane", "ROLE_ADMIN");
			userService.addRoleToUser("smith", "ROLE_ADMIN");
			userService.addRoleToUser("smith", "ROLE_SUPER_ADMIN");
		};
	}

}
