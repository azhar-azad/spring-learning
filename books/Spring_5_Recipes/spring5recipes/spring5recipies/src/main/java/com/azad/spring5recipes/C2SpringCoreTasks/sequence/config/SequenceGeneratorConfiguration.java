/**
 * Create a Java Config with @Configuration and @Bean to Create POJOs
 * 
 * To define instances of a POJO class in the Spring IoC container, you can create a Java config class with
 * instantiation values. A Java config class with a POJO or bean definition looks like this.
 * 
 * @Configuration:
 * Notice the SequenceGeneratorConfiguration class is decorated with the @Configuration annotation;
 * this tells Spring it’s a configuration class. When Spring encounters a class with the @Configuration
 * annotation, it looks for bean instance definitions in the class, which are Java methods decorated with the
 * @Bean annotation. The Java methods create and return a bean instance.
 * 
 * @Bean:
 * Any method definitions decorated with the @Bean annotation generates a bean name based on the
 * method name. Alternatively, you can explicitly specify the bean name in the @Bean annotation with the name
 * attribute. For example, @Bean(name="mys1") makes the bean available as mys1.
 * */

package com.azad.spring5recipes.C2SpringCoreTasks.sequence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azad.spring5recipes.C2SpringCoreTasks.sequence.SequenceGenerator;

@Configuration
public class SequenceGeneratorConfiguration {

	@Bean
	public SequenceGenerator sequenceGenerator() {
		SequenceGenerator seqgen = new SequenceGenerator();
		seqgen.setPrefix("30");
		seqgen.setSuffix("A");
		seqgen.setInitial(100000);
		return seqgen;
	}
}
