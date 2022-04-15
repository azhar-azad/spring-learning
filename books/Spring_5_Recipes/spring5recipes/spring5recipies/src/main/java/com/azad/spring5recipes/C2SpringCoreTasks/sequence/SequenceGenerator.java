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
 * */

package com.azad.spring5recipes.C2SpringCoreTasks.sequence;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {
	
//	@Autowired
//	private PrefixGenerator[] prefixGenerators;
	
	private String prefix;
	private String suffix;
	private int initial;
	private final AtomicInteger counter = new AtomicInteger();
	
	public SequenceGenerator() {
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setInitial(int initial) {
		this.initial = initial;
	}
	
	public String getSequence() {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix).append(initial).append(counter.getAndIncrement()).append(suffix);
		return builder.toString();
	}

	public void setPrefixGenerator(DatePrefixGenerator datePrefixGenerator) {
		// TODO Auto-generated method stub
		
	}

}
