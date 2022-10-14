package com.mdf.springjpa.Srping.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
		
		/**
		 * Configuration to deny all the requests
		 */
		
		/*http.authorizeRequests()
				.anyRequest()
				.denyAll()
				.and().formLogin()
				.and().httpBasic();
				*/
		/**
		 * Permit all request
		 */
		
		/*http.authorizeRequests()
		.anyRequest()
		.permitAll()
		.and().formLogin()
		.and().httpBasic();
		*/
		
		http.authorizeRequests()
		.antMatchers("/api/**").authenticated()
		.antMatchers("/api/student/register").permitAll()
		.and().formLogin()
		.and().httpBasic()
		.and().csrf().disable();
		
		return http.build();
	}
	
	/**
	 * NoOpPasswordEncoder no es seguro
	 * @return
	 */
	/*@Bean
	public PasswordEncoder passwordEncoder() {		
		return NoOpPasswordEncoder.getInstance();		
	}*/
	
	@Bean
	public PasswordEncoder passwordEncoder() {		
		return new BCryptPasswordEncoder();		
	}
}
