package com.hkexcel.hkexcelapirestful.model;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Student extends Person {

	@Id
	private String id;
	private School school;

	public Student(String firstName, String lastName, String Address, int age, Date dateOfBirth) {
		super(firstName, lastName, Address, age, dateOfBirth);
	}

	
	
	
}
