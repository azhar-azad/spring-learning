package com.azad.tacocloud.tacos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/***
 * At the center of Spring MVC is the concept of a controller, a class that handles requests and responds with
 * information of some sort. In the case of a browser-facing application, a controller responds by optionally
 * populating model data and passing the request on to a view to produce HTML that's returned to the browser.
 *
 * @Controller - It's primary purpose is to identify this class as a component for component scanning. Because
 * HomeController is annotated with @Controller, Spring's component scanning automatically discovers it and creates an
 * instance of HomeController as a bean in the Spring application context.
 */
@Controller
public class HomeController {

    /***
     * @GetMapping - to indicate that if an HTTP GET request is received for the root path /, then this method should
     * handle that request. It does so by doing nothing more than returning a String value of home. The value is
     * interpreted as the logical name of a view.
     * @return the logical name of a view.
     */
    @GetMapping("/")
    public String home() {

        /*
        The template name is derived from the logical view name by prefixing it with /templates/ and post-fixing it with
        .html. The resulting path for the template is /templates/home.html.
         */
        return "home";
    }
}
