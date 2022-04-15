/**
 * 2-3. Use POJO References and Autowiring to Interact with Other POJOs
 * */

package com.azad.spring5recipes.C2SpringCoreTasks.sequence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azad.spring5recipes.C2SpringCoreTasks.sequence.DatePrefixGenerator;
import com.azad.spring5recipes.C2SpringCoreTasks.sequence.SequenceGenerator;

@Configuration
public class SequenceConfiguration {

	@Bean
	public DatePrefixGenerator datePrefixGenerator() {
		DatePrefixGenerator dpg = new DatePrefixGenerator();
		dpg.setPattern("yyyyMMdd");
		return dpg;
	}
	
	@Bean
	public SequenceGenerator sequenceGenerator() {
		SequenceGenerator sequence = new SequenceGenerator();
		sequence.setInitial(100000);
		sequence.setSuffix("A");
		sequence.setPrefixGenerator(datePrefixGenerator());
		return sequence;
	}
}
