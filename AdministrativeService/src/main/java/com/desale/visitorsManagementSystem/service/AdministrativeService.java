package com.desale.visitorsManagementSystem.service;

import java.util.List;

import com.desale.visitorsManagementSystem.service.req.ResponseUser;

public interface AdministrativeService {
	List<ResponseUser> getUsers();
	
}
