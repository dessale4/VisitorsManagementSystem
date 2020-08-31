package com.desale.visitorsManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desale.visitorsManagementSystem.model.User;
import com.desale.visitorsManagementSystem.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	@PostMapping
	public User addUser(@RequestBody User user) {
		System.out.println(user);
		return userService.addUser(user);
	}
	
	@GetMapping
	public List<User> getUsers(){
		System.out.println(userService.getUsers());
		return userService.getUsers();
	}
}
