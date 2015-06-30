package com.eleks.service;

import com.eleks.exception.AuthenticationException;

public interface AuthService {
	void validateCredentials(String userName, String userPass) throws AuthenticationException;
}
