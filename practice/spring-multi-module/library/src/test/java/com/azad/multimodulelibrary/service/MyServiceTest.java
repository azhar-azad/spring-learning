package com.azad.multimodulelibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/***
 * In the following listing, we have configured the service.message for the test by using the default attribute of the
 * @SpringBootTest annotation. We do not recommend putting application.properties in a library, because there might be
 * a clash at runtime with the application that uses the library (only one application.properties is ever loaded from
 * the classpath). You could put application.properties in the test classpath but not include it in the jar
 * (for instance, by placing it in src/test/resources).
 */
@SpringBootTest("service.message=Hello")
public class MyServiceTest {

    @Autowired
    private MyService myService;

    @Test
    public void contextLoads() {
        assertThat(myService.message()).isNotNull();
    }

    @SpringBootApplication
    static class TestConfiguration {

    }
}
