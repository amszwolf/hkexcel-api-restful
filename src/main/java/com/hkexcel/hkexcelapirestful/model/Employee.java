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
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Employee extends Person{

	@Id
	private String id;
	
	public Employee(String firstName, String lastName, String address, int age, Date dateOfBirth) {
		super(firstName, lastName, address, age, dateOfBirth);
	}

	
}
