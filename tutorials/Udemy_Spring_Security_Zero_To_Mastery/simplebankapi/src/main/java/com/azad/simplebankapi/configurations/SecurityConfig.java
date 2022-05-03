package com.azad.simplebankapi.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * 	Secured routes => {
	 *  	/accounts
	 *  	/balances
	 *  	/loans
	 *  	/cards
	 *  }
	 *  
	 *  Unsecured/No-auth routes => {
	 *  	/contacts
	 *  	/notices
	 *  }
	 * */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/**
		 * Default configuration which will secure all the requests
		* */		 
//		http
//			.authorizeRequests()
//				.anyRequest().authenticated()
//			.and()
//			.formLogin()
//			.and()
//			.httpBasic();
			
		/**
		 * Configuration to deny all the requests whether they are authenticated or not
		 * */
//		http
//			.authorizeRequests()
//				.anyRequest().denyAll()
//			.and()
//			.formLogin()
//			.and()
//			.httpBasic();
		
		/**
		 * Configuration to permit all the requests 
		 * */
//		http
//			.authorizeRequests()
//				.anyRequest().permitAll()
//			.and()
//			.formLogin()
//			.and()
//			.httpBasic();

		
		/**
		 * Custom configuration as per requirement (4 secured routes and 2 unsecured routes)
		 * 
		* */		 
		http
			.authorizeRequests()
				.antMatchers("/api/v1/accounts").authenticated() 	
				.antMatchers("/api/v1/balances").authenticated() 
				.antMatchers("/api/v1/loans").authenticated() 	
				.antMatchers("/api/v1/cards").authenticated() 	
				.antMatchers("/api/v1/contacts").permitAll() 	
				.antMatchers("/api/v1/notices").permitAll() 	
			.and()
			.formLogin() 	
			.and()
			.httpBasic();
			

	}

}
