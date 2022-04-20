/**
 * SpEL (Spring Expression Language)
 * Try out this link for quick understanding: https://www.baeldung.com/spring-expression-language
 * The access() method takes an expression by which expressing the security rule can be much more flexible. 
 * */

package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.User;
import tacos.data.UserRepository;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		return username -> {
			User user = userRepository.findByUsername(username);
			if (user != null) {
				return user;
			}

			throw new UsernameNotFoundException("User '" + username + "' not found");
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				// permitting access for various routes based on role
				.authorizeRequests()
					.antMatchers("/design", "/orders").access("hasRole('USER')")
					.antMatchers("/", "/**").access("permitAll()")
				// replacing the build-in login page with custom login page
				.and()
					.formLogin()
						.loginPage("/login")
						.defaultSuccessUrl("/design")
				// enabling OAuth2 login
//				.and()
//					.oauth2Login()

				.and()
				.build();
	}
}
