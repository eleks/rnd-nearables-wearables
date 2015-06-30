package com.eleks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 4221924034676706149L;
	
	public ValidationException(String message) {
		super(message);
	}

}
