package com.hkexcel.hkexcelapirestful.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public abstract class Person {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String Address;
	private int age;
	private Date dateOfBirth;
	
	public Person(String firstName, String lastName, String address, int age, Date dateOfBirth) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		Address = address;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
	}
	
	
	
}
