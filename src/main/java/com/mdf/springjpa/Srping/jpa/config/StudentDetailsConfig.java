package com.mdf.springjpa.Srping.jpa.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mdf.springjpa.Srping.jpa.models.Authority;
import com.mdf.springjpa.Srping.jpa.models.Student;
import com.mdf.springjpa.Srping.jpa.repository.StudentRepository;

@Service
public class StudentDetailsConfig implements AuthenticationProvider {//UserDetailsService {
	
	@Autowired
	private StudentRepository _studentRepository;
	
	@Autowired
	private PasswordEncoder _passwordEncoder;
	
	/*
	@Override
	public UserDetails Authentication loadUserByUsername(String username) throws UsernameNotFoundException {
		
		String userName, password = null;
		List<GrantedAuthority> authorities = null;
		
		Student student = this._studentRepository.findByEmailId(username);
		
		if(student == null) {
			throw new UsernameNotFoundException("User details was not founded");
		}else {
			if(_passwordEncoder.matches(password, student.getPassword())) {
				authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(student.getRole()));
			}			
		}
		
		System.out.println(userName);
		
		return new User(userName,password,authorities);
	} 
*/
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		Student student = this._studentRepository.findByEmailId(userName);
		if(student != null) {
			if(_passwordEncoder.matches(password, student.getPassword())) {
				List<GrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(student.getRole()));
				return new UsernamePasswordAuthenticationToken(userName, password, getGrantedAuthorities(student.getAuthorities()));
			}
			throw new BadCredentialsException("No user registered with those credentials");
		}		
		throw new BadCredentialsException("No user registered with those credentials");
	}
	
	private List<GrantedAuthority> getGrantedAuthorities (Set<Authority> authorities){
		List<GrantedAuthority> grandedAuthorities = new ArrayList<>();
		
		for(Authority authority : authorities) {
			grandedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
		}
		
		return grandedAuthorities;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
