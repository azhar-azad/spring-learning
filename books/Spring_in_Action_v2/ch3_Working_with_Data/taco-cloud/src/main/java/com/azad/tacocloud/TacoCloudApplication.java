package com.azad.tacocloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 * Bootstrapping the application
 * As this application will be run from an executable JAR, it's important to have a main class that will be executed
 * when the JAR file is run.
 *
 * @SpringBootAppliation annotation clearly signifies that this is a Spring Boot application. It is a composite
 * annotation that combines the following three annotations:
 * 		@SpringBootConfiguration - Designates this class as a configuration class. This annotation is, in fact, a
 * 		specialized form of the @Configuration annotation.
 * 		@EnableAutoConfiguration - Enables Spring Boot automatic configuration. This annotation tells Spring Boot to
 * 		automatically configure any components that it thinks we'll need.
 * 		@ComponentScan - Enables component scanning. This lets us declare other classes with annotations like
 * 		@Component, @Controller, and @Service to have Spring automatically discover and register them as components in
 * 		the Spring application context.
 */
@SpringBootApplication
public class TacoCloudApplication {

	/**
	 * This is the method that will be run when the JAR file is executed.
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		The main() method calls a static run() method on the SpringApplication class, which performs the actual
		bootstrapping of the application, creating the Spring application context.
		 */
		SpringApplication.run(TacoCloudApplication.class, args);

		/*
		Testing the application
		We can test this project manually by building it and then running it from the command line like the following:
			./mvnw package
			... and then
			java -jar target/taco-cloud-0.0.1-SNAPSHOT.jar

		Or, because we're using Spring Boot, the Spring Boot Maven plugin makes it even easier, as following:
			./mvnw spring-boot:run
		 */
	}

}
