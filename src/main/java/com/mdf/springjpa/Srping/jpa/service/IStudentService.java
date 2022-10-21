package com.mdf.springjpa.Srping.jpa.service;

import java.util.List;

import com.mdf.springjpa.Srping.jpa.models.Student;

public interface IStudentService {
	
	Student addStudent(Student student);
	
	Student findStudentByEmail(String email);
	
	List<Student> retrieveAllStudent();
	
	Boolean updateStudent(Student student);
	
	Boolean removeStudent(Student student);
	
	List<Student> retrieveAllStudentsAuthorities();

}
