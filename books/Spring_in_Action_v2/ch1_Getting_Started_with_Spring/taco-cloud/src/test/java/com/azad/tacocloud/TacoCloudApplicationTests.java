package com.azad.tacocloud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/***
 * The test class does perform an essential check to ensure that the Spring application context can be loaded
 * successfully.
 *
 * @SpringBootTest - annotation tells JUnit to bootstrap the test with Spring Boot capabilities. It is a composite
 * annotation, which is itself annotated with @ExtendWith(SpringExtension.class), to add Spring testing capabilities to
 * JUnit 5.
 */
@SpringBootTest
class TacoCloudApplicationTests {

	/**
	 * Even without any assertions or code of any kind, this empty test method will prompt the two annotations to do
	 * their job and load the Spring application context. If there are any problems in doing so, the test fails.
	 * To run this and any test classes from the command line, we can use the following Maven incantation:
	 * 	./mvnw test
	 */
	@Test
	void contextLoads() {
	}

}
