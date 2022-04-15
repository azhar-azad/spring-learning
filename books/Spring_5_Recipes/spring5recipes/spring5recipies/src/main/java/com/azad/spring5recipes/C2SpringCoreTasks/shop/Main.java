/**
 * 2-2. Create POJOs by Invoking a Constructor
 * 
 * Problem
 * -------
 * You want to create a POJO instance or bean in the Spring IoC container by invoking its constructor, which
 * is the most common and direct way of creating beans. It is equivalent to using the new operator to create
 * objects in Java.
 * 
 * Solution
 * --------
 * Define a POJO class with a constructor or constructors. Next, create a Java config class to configure POJO
 * instance values with constructors for the Spring IoC container. Next, instantiate the Spring IoC container to
 * scan for Java classes with annotations. The POJO instances or bean instances become accessible to be put
 * together as part of an application.
 * 
 * ==============================================================================================================
 * */

package com.azad.spring5recipes.C2SpringCoreTasks.shop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.azad.spring5recipes.C2SpringCoreTasks.shop.config.ShopConfiguration;

public class Main {

	public static void main(String[] args) {

		ApplicationContext contex = new AnnotationConfigApplicationContext(ShopConfiguration.class);
		
		// Bean class name is passed to avoid making a cast to that class.
		Product aaa = contex.getBean("aaa", Product.class);
		Product cdrw = contex.getBean("cdrw", Product.class);
		
		System.out.println(aaa);
		System.out.println(cdrw);
	}

}
