package com.hkexcel.hkexcelapirestful.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SchoolNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SchoolNotFoundException(String id) {
		super("Could not find school with id:  " + id);
	}
}
