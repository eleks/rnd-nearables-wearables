package com.eleks.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eleks.service.UserService;

@Component
public class AccessTokenValidator implements Validator {
	
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
//		final String accessToken = (String) target;
//		if(accessToken != null) {
//			final boolean activated = userService.validateAccessToken(accessToken);
//			if(!activated) {
//				errors.reject(HttpStatus.FORBIDDEN.getReasonPhrase(), "Invalid access token");
//			}
//		}
//		
//		errors.reject(HttpStatus.FORBIDDEN.getReasonPhrase(), "Access token cannot be null");
	}

}
