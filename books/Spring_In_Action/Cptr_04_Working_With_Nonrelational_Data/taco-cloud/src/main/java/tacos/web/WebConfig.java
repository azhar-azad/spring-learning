/**
 * View Controller
 * A controller that does nothing but forward the request to a view. 
 * 
 * WebMvcConfigurer
 * An Interface that defines several methods for configuring Spring MVC.
 * 
 * addViewControllers()
 * The addViewControllers() method is given a ViewControllerRegistry that 
 * can be used to register one or more view controllers.
 * 
 * ViewControllerRegistry
 * addViewController() is called on the registry, 
 * passing in “/”, which is the path for which the view controller will 
 * handle GET requests. That method returns a ViewControllerRegistration object, on 
 * which setViewName() is immediately called to specify home as the view that a request 
 * for “/” should be forwarded to.
 * */

package tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}
}
