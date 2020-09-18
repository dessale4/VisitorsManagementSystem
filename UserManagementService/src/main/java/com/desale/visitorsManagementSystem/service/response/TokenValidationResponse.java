package com.desale.visitorsManagementSystem.service.response;

import java.util.List;

public class TokenValidationResponse {

	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String userType;
	private List<String> authorites;
	private boolean isValid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getAuthorites() {
		return authorites;
	}

	public void setAuthorites(List<String> authorites) {
		this.authorites = authorites;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
