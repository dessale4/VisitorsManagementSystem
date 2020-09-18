package com.desale.visitorsManagementSystem.exception.custom;

public class AuthenticationException extends RuntimeException {
	
	//class constructor
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	// class constructor
	public AuthenticationException(String message) {
		super(message);
	}
}