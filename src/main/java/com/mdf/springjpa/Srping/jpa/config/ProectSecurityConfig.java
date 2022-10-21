package com.mdf.springjpa.Srping.jpa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.mdf.springjpa.Srping.jpa.filter.AuthoritiesLoggingAfterFilter;
import com.mdf.springjpa.Srping.jpa.filter.AuthoritiesLoggingAtFilter;
import com.mdf.springjpa.Srping.jpa.filter.RequestValidationBeforeFilter;

@Configuration
public class ProectSecurityConfig {

	@Value("${URLS.Authenticated.WithoutRole}")
	private String authenticatedURLWithoutRoles;
	
	@Value("${URLS.Authenticated.WithBalanceRole}")
	private String authenticatedURLWithBalanceRole;
	
	@Value("${URLS.Permitall}")
	private String permitedURL;
	
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
		
		String[] listUrlAuthenticated = this.authenticatedURLWithoutRoles.split(",");
		String[] listUrlAuthenticatedWithBalanceRole = this.authenticatedURLWithBalanceRole.split(",");
		String[] listUrlPermited = this.permitedURL.split(",");
		
		http.authorizeRequests()
		.antMatchers(listUrlPermited).permitAll()
		.antMatchers(listUrlAuthenticated).authenticated()
		.antMatchers(listUrlAuthenticatedWithBalanceRole).hasAuthority("VIEWBALANCE")		
		.and().formLogin()
		.and().httpBasic()
		.and().csrf().disable()
		.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
		.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
		.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
		;
		
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
