package com.eleks.model;

public class UserCredentials {

	private final String userName;
	private final String password;
	
	public UserCredentials(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
}
