package com.desale.visitorsManagementSystem.service;

import java.util.List;

import com.desale.visitorsManagementSystem.model.User;

public interface UserService {
	
	public User addUser(User user);
	public List<User> getUsers();
}
