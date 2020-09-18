package com.desale.visitorsManagementSystem.service;

import com.desale.visitorsManagementSystem.enums.RoleType;
import com.desale.visitorsManagementSystem.exception.custom.EmailAlreadyExistException;
import com.desale.visitorsManagementSystem.service.request.UserRegistrationRequest;

public interface UserRegistrationService {
	
	public void saveUser(UserRegistrationRequest registrationForm, RoleType roleType)throws EmailAlreadyExistException;
}
