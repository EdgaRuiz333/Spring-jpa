package com.mdf.springjpa.Srping.jpa.service.Impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdf.springjpa.Srping.jpa.models.Guardian;
import com.mdf.springjpa.Srping.jpa.models.Student;
import com.mdf.springjpa.Srping.jpa.repository.StudentRepository;
import com.mdf.springjpa.Srping.jpa.service.IStudentService;

@Service
@Transactional
public class StudentServiceImpl implements IStudentService {

	@Autowired
	StudentRepository _studentRepository;
	@Autowired
	PasswordEncoder _passwordEncoder;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Student addStudent(Student student) {
		// TODO Auto-generated method stub
		
		Guardian guardian = Guardian.builder()
								.name(student.getGuardian().getName())
								.email(student.getGuardian().getEmail())
								.mobile(student.getGuardian().getMobile())
								.build();
		
		Student bldStudent = Student.builder()
							.fisrtName(student.getFisrtName())
							.lastName(student.getLastName())
							.emailId(student.getEmailId())
							.password(_passwordEncoder.encode(student.getPassword()))
							.role(student.getRole())
							.guardian(guardian)
							.build();
		this._studentRepository.save(bldStudent);
		return bldStudent;
	}

	@Override
	@Transactional
	public Student findStudentByEmail(String email) {
		// TODO Auto-generated method stub
		return this._studentRepository.findByEmailId(email);
	}
	
	@Override
	@Transactional
	public List<Student> retrieveAllStudent() {
		// TODO Auto-generated method stub
		//return this._studentRepository.findAll();
		return this._studentRepository.GET_ALL_STUDENTS();
	}

	@Override
	public Boolean updateStudent(Student student) {
		// TODO Auto-generated method stub
		try {
			Guardian _guardian = Guardian.builder()
									.name(student.getGuardian().getName())
									.email(student.getGuardian().getEmail())
									.mobile(student.getGuardian().getMobile())
									.build();
			
			Student studentUpdated = Student.builder()
					.studentId(student.getStudentId())
					.fisrtName(student.getFisrtName())
					.lastName(student.getLastName())
					.emailId(student.getEmailId())
					.guardian(_guardian)
					.build();
			
			this._studentRepository.save(studentUpdated);
			return true;			
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean removeStudent(Student student) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional
	public List<Student> retrieveAllStudentsAuthorities(){
		try {
			List studentAuth = this.em.createStoredProcedureQuery("GET_ALL_STUDENTS_AUTHORITIES")
					.registerStoredProcedureParameter("email", String.class, ParameterMode.IN)
					.setParameter("email", "test@test.com")
					.getResultList();
			
			//List<Student> studentAuth = this._studentRepository.GET_ALL_STUDENTS_AUTHORITIES("test@test.com");
			return studentAuth;
		} catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
}
