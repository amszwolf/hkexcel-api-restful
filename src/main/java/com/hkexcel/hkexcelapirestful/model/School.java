package com.hkexcel.hkexcelapirestful.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
@Document(collection="schools")
public class School {
	
	@Id
	private String id;
	private String schoolCode;
	private String fullName;
	private String country;
	private String city;
	private String address;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date createdAt;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date updatedAt;
	
}
