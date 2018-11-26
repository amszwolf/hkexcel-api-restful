package com.hkexcel.hkexcelapirestful.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class SchoolNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(SchoolNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String schoolNotFoundHandler(SchoolNotFoundException ex) {
		return ex.getMessage();
	}
}