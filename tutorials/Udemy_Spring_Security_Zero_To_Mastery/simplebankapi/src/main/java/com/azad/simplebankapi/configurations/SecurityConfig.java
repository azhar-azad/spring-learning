package com.azad.simplebankapi.configurations;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		/**
		 * InMemoryAuthentication
		 * */
//		auth
//			.inMemoryAuthentication()
//				.withUser("admin").password("1234").authorities("admin")
//				.and()
//				.withUser("user").password("1234").authorities("read")
//			.and()
//			.passwordEncoder(NoOpPasswordEncoder.getInstance());
		
		/**
		 * InMemoryUserDetailsManager - An implementation of UserDetails
		 * 
		 * Make sure to provide spring boot a PasswordEncoder bean.
		 * */
//		UserDetails user1 = User.withUsername("admin").password("1234").authorities("admin").build();
//		UserDetails user2 = User.withUsername("azad").password("1234").authorities("read").build();
//		
//		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//		userDetailsService.createUser(user1);
//		userDetailsService.createUser(user2);
//		
//		auth.userDetailsService(userDetailsService);
		
		/**
		 * JdbcUserDetailsManager implementation (no customization)
		 * 
		 * If we want the default JdbcUserDetailsManager implementation (no customization on the database end),
		 * then we do not even need to override this configure(auth) method.
		 * We just need to create a Bean to return the UserDetailsService
		 * */
	
		
		/**
		 * JdbcUserDetailsManager implementation (with customization)
		 * 
		 * Have an entity class that will implement UserDetails interface.
		 * Have a service class that will implement either UserDetailsService or UserDetailsManager interface. 
		 * Write custom implementation on the loadUserByUsername method and return a UserDetails interface implementation. 
		 * No need to override this configure(auth) method.
		 * No need define a Bean to return the UserDetailsService.
		 * */
		
//	}
	
//	@Bean
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		// the datasource will be created automatically by the spring provided the database configuration on the application.properties
//		return new JdbcUserDetailsManager(dataSource);
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
	

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
