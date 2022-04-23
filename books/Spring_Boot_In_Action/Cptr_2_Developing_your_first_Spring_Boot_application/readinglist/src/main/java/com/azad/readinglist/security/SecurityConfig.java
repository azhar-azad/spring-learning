package com.azad.readinglist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.azad.readinglist.repos.ReaderRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private ReaderRepository readerRepository;

	@Autowired
	public SecurityConfig(ReaderRepository readerRepository) {
		this.readerRepository = readerRepository;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/").access("hasRole('READER')") // Require READER access
				.antMatchers("/**").permitAll()
				
			.and()
			
			.formLogin()
				.loginPage("/login") // Set login form path
				.failureUrl("/login?error=true");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(new UserDetailsService() { // Define custom UserDetailsService
				@Override
				public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
					return readerRepository.findById(username).orElse(null);
				}
			});
	}
}
