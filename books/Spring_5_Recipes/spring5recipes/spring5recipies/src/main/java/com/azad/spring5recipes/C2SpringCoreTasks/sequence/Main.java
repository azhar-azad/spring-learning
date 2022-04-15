/**
 * 2-1. Use a Java Config to Configure POJOs
 * 
 * Problem
 * -------
 * You want to manage POJOs with annotations with Spring's IoC container.
 * 
 * Solution
 * --------
 * Design a POJO class. Next, create a Java config class with @Configuration and @Bean annotations to
 * configure POJO instance values or set up Java components with @Component, @Repository, @Service, or
 * @Controller annotations to later create POJO instance values. Next, instantiate the Spring IoC container to
 * scan for Java classes with annotations. The POJO instances or bean instances then become accessible to put
 * together as part of an application.
 * 
 * ==============================================================================================================
 * 
 * Get POJO Instances or Beans from the IoC Container
 * 
 * You have to instantiate the Spring IoC container to scan for Java classes that contain annotations. In doing
 * so, Spring detects @Configuration and @Bean annotations so you can later get bean instances from the IoC
 * container itself.
 * 
 * Spring provides two types of IoC container implementations. The basic one is called a bean factory. The
 * more advanced one is called an application context, which is compatible with the bean factory.
 * 
 * The interfaces for the bean factory and the application context are BeanFactory and
 * ApplicationContext, respectively.
 * */

package com.azad.spring5recipes.C2SpringCoreTasks.sequence;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.azad.spring5recipes.C2SpringCoreTasks.sequence.config.SequenceGeneratorConfiguration;

public class Main {

	public static void main(String[] args) {

		ApplicationContext context = new AnnotationConfigApplicationContext(SequenceGeneratorConfiguration.class);
		
		// If there is only a single bean, you can omit the bean name.
		SequenceGenerator generator = context.getBean(SequenceGenerator.class);
		
		System.out.println(generator.getSequence());
		System.out.println(generator.getSequence());
	}

}

/**
 * 301000000A
 * 301000001A
 * */
