package com.hkexcel.hkexcelapirestful.exception;

class SchoolNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	SchoolNotFoundException(String id) {
		super("Could not find student " + id);
	}
}
