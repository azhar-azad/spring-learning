/**
 * 2-1. Use a Java Config to Configure POJOs
 * 
 * Get POJO Instances or Beans from the IoC Container
 * */

package com.azad.spring5recipes.C2SpringCoreTasks.sequence;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main2 {

	public static void main(String[] args) {

		ApplicationContext context = new AnnotationConfigApplicationContext("com.azad.spring5recipes.C2SpringCoreTasks.sequence");
		
		SequenceDao sequenceDao = context.getBean(SequenceDao.class);
		
		System.out.println(sequenceDao.getNextValue("IT"));
		System.out.println(sequenceDao.getNextValue("IT"));
	}

}

/**
 * 100000
 * 100001
 * */
