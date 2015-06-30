package com.eleks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidEmailException extends RuntimeException {

	private static final long serialVersionUID = -3515956097802743585L;

	public InvalidEmailException(String message) {
		super(message);
	}
	
}
