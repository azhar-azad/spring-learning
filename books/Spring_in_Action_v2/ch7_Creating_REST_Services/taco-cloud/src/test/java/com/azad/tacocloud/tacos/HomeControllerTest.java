package com.azad.tacocloud.tacos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/***
 * HomeControllerTest is annotated with @WebMvcTest, instead of @SpringBootTest markup.
 * @WebMvcTest - is a special test annotation provided by Spring Boot that arranges for the test to run in the context
 * of a Spring MVC application. In this case, it arranges for HomeController to be registered in Spring MVC so that we
 * can send requests to it. It could be made to start a server, mocking the mechanics of Spring MVC is sufficient for
 * our purpose.
 */
@WebMvcTest(HomeController.class) // Web test for HomeController
public class HomeControllerTest {

    /*
    The test class is injected with a MockMvc object for the test to drive the mockup.
     */
    @Autowired
    private MockMvc mockMvc; // Injects MockMvc

    /***
     * The testHomePage() method defines the test we want to perform against the home page. It starts with the MockMvc
     * object to perform an HTTP GET request for / (the root path). From that request, it sets the following
     * expectations:
     *      - The response should have an HTTP 200 (OK) status.
     *      - The view should have a logical name of home.
     *      - The rendered view should contain the text "Welcome to...".
     *
     * We can run the test in the terminal with Maven like this:
     *      ./mvnw test
     * @throws Exception
     */
    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))   // Performs GET /
                .andExpect(status().isOk())     // Excepts HTTP 200
                .andExpect(view().name("home"))     // Expects home view
                .andExpect(content().string(
                        containsString("Welcome to...")));  // Expects Welcome to...
    }
}
