package com.azad.tacocloud.tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/***
 * When a controller is simple enough that it doesn't populate a model or process input - as is the case with our
 * HomeController - there's another way that we can define the controller - a controller that does nothing but forward
 * the request to a view.
 *
 * The WebConfig class implements the WebMvcConfigurer interface. This interface defines several methods for configuring
 * Spring MVC.
 *
 * This class can replace the HomeController class and, we can now delete the HomeController, and the application should
 * still behave as it did before.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /***
     * This method is used to add various view controllers.
     * @param registry That we can use to register one or more view controllers.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /*
        We call addViewController() on the registry, passing in "/", which is the path for which our view controller
        will handle GET requests. That method returns a ViewControllerRegistration object, on which we immediately call
        setViewName() to specify 'home' as the view that a request for "/" should be forwarded to.
         */
        registry.addViewController("/").setViewName("home");

        /*
        We need to provide a controller that handles requests at the login path. Because our login page will be fairly
        simple - nothing but a view - it's easy enough to declare it as a view controller.
         */
        registry.addViewController("/login");
    }
}
