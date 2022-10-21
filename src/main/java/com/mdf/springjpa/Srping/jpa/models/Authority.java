package com.mdf.springjpa.Srping.jpa.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="authoritites")
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name="authorities_id")
	private Long id;
		
	private String name;
	
	@ManyToMany(mappedBy = "authorities")
	@JsonIgnore
	@ToString.Exclude
	private Set<Student> student;

}
