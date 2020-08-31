package com.desale.visitorsManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desale.visitorsManagementSystem.service.AdministrativeService;
import com.desale.visitorsManagementSystem.service.req.ResponseUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/administrative")

public class AdministrativeController {
	@Autowired
	AdministrativeService administrativeService;
	
	@ApiOperation(value="List of visitors", notes="daily visitors list", response= ResponseUser.class)
	@GetMapping("/getUsers")
	public List<ResponseUser> getUsers(){
		return administrativeService.getUsers();
	}
	
}
