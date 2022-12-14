package com.mdf.springjpa.Srping.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdf.springjpa.Srping.jpa.models.Student;
import com.mdf.springjpa.Srping.jpa.repository.StudentRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private StudentRepository _studentRepository;
	
	@PostMapping("/signin")
	public ResponseEntity<Student> authenticateUser(Authentication authentication){
		
		Student _student = _studentRepository.findByEmailId(authentication.getName());
		if(_student != null) {
			return new ResponseEntity<>(_student,HttpStatus.OK);
		}else {
			return null;
		}		
	}

}
