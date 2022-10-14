package com.mdf.springjpa.Srping.jpa.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="tbl_students", uniqueConstraints = @UniqueConstraint(name="email_unique", columnNames = "email_address"))
public class Student {
	@Id
	@SequenceGenerator(name="student_sequence", sequenceName = "student_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
	private Long studentId;
	@Column(columnDefinition = "varchar(50)")
	@NotBlank(message = "Field 'firstName' is mandatory")
	@Size(min = 1, max = 25, message = "Name needs to have at least 2 letters")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Name just allow letters")
	private String fisrtName;
	private String lastName;
	@Column(name="email_address",nullable=false)
	@NotBlank(message = "Field 'Email' is mandatory")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email format is not correct")
	private String emailId;
	@NotBlank(message = "Field 'Password' is mandatory")
	private String password;
	@NotBlank(message = "Field 'Role' is mandatory")
	private String role;
	
	@Embedded
	private Guardian guardian;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Course> courses;
}