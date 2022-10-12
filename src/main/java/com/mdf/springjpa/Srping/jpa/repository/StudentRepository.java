package com.mdf.springjpa.Srping.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.mdf.springjpa.Srping.jpa.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	public Student findByEmailId(String emailId);
	
	public List<Student> findByGuardianNameNotNull();
	
	@Procedure
	public List<Student> GET_ALL_STUDENTS();
	
}
