package com.mdf.springjpa.Srping.jpa.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mdf.springjpa.Srping.jpa.models.Course;
import com.mdf.springjpa.Srping.jpa.service.ICourseService;

@RestController
@RequestMapping("/api/course")
public class CourseController {

	@Autowired
	private ICourseService _courseService;
	
	@PostMapping
	public ResponseEntity<Boolean> addCourse(
			@Valid @RequestBody Course course
			){		
		Boolean success = this._courseService.addCourse(course);	
		if(success)
			return new ResponseEntity<>(success, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);			
	}
}
