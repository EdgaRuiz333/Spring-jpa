package com.mdf.springjpa.Srping.jpa.config;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.mdf.springjpa.Srping.jpa.filter.AuthoritiesLoggingAfterFilter;
import com.mdf.springjpa.Srping.jpa.filter.AuthoritiesLoggingAtFilter;
import com.mdf.springjpa.Srping.jpa.filter.RequestValidationBeforeFilter;
import com.mdf.springjpa.Srping.jpa.security.JWTTokenGeneratorFilter;
import com.mdf.springjpa.Srping.jpa.security.JWTTokenValidatorFilter;


@Configuration
public class ProectSecurityConfig{

	@Value("${URLS.Authenticated.WithoutRole}")
	private String authenticatedURLWithoutRoles;
	
	@Value("${URLS.Authenticated.WithBalanceRole}")
	private String authenticatedURLWithBalanceRole;
	
	@Value("${URLS.Permitall}")
	private String permitedURL;
	
	@Bean
	protected SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
		
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
		
		http
		.securityContext()
		.and().cors().configurationSource(
				new CorsConfigurationSource() {

					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						// TODO Auto-generated method stub
						CorsConfiguration config = new CorsConfiguration();
						config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
						config.setAllowedMethods(Collections.singletonList("*"));
						config.setAllowCredentials(true);
						config.setAllowedHeaders(Collections.singletonList("*"));
						config.setMaxAge(3600L);
						config.addExposedHeader("Authorization");
						return config;
					}
					
				})
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)		
		.and().csrf().ignoringAntMatchers(listUrlPermited).csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.and().authorizeRequests()
		.antMatchers(listUrlPermited).permitAll()
		.antMatchers(listUrlAuthenticated).authenticated()
		.antMatchers(listUrlAuthenticatedWithBalanceRole).hasAuthority("VIEWBALANCE")		
		.and().formLogin()
		.and().httpBasic()
		.and().addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
		.addFilterAfter(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
		//.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
		//.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
		//.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
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
	
	@Bean
	public Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}
	
}
