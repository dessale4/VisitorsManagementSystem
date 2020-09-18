package com.desale.visitorsManagementSystem.service.request;

import java.io.Serializable;

public class TokenValidationRequest implements Serializable {

	private static final long serialVersionUID = 8317676219297719109L;

	private String token;

	public TokenValidationRequest() {

	}

	public TokenValidationRequest(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
