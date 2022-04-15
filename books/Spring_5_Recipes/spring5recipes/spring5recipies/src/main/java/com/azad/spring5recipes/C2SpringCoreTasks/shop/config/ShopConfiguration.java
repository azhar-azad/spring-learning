/**
 * Create a Java Config for Your POJO
 * 
 * To define instances of a POJO class in the Spring IoC container, you have to create a Java config class with
 * instantiation values. A Java config class with a POJO or bean definition made by invoking constructors would
 * look like this.
 * */

package com.azad.spring5recipes.C2SpringCoreTasks.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azad.spring5recipes.C2SpringCoreTasks.shop.Battery;
import com.azad.spring5recipes.C2SpringCoreTasks.shop.Disc;
import com.azad.spring5recipes.C2SpringCoreTasks.shop.Product;

@Configuration
public class ShopConfiguration {

	@Bean
	public Product aaa() {
		Battery p1 = new Battery("AAA", 2.5);
		p1.setRechargeable(true);
		return p1;
	}
	
	@Bean
	public Product cdrw() {
		Disc p2 = new Disc("CD-RW", 1.5);
		p2.setCapacity(700);
		return p2;
	}
}
