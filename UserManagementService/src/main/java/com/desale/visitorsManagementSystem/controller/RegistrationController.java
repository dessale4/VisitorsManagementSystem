package com.desale.visitorsManagementSystem.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desale.visitorsManagementSystem.enums.RoleType;
import com.desale.visitorsManagementSystem.exception.custom.EmailAlreadyExistException;
import com.desale.visitorsManagementSystem.service.request.UserRegistrationRequest;
import com.desale.visitorsManagementSystem.service.UserRegistrationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user/registration")
@Api(value = "Registration Controller", description = "This Controller contains APIs for Registry of Employees and Guests ")
public class RegistrationController {

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	@Value("${jwt.get.token.uri}") //the end point uri is indicated in application.properties file as "/refresh"
	private String authenticationPath;

	@Autowired
	private UserRegistrationService registrationService;

	@RequestMapping(value = "/employee", method = RequestMethod.POST)
	@ApiOperation(value = "Register new User as an Employee")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Regular user Added and waiting for confirmation by email"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<?> registerUser(@RequestBody  UserRegistrationRequest registrationForm)
			throws EmailAlreadyExistException, URISyntaxException {

		registrationService.saveUser(registrationForm, RoleType.ROLE_EMPLOYEE);
		return ResponseEntity.created(new URI(authenticationPath)).build();////the end point uri is indicated in application.properties file as "/refresh"
	}

	@RequestMapping(value = "/guest", method = RequestMethod.POST)
	@ApiOperation(value = "Register new User as a Guest")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Admin user Added and waiting for confirmation by email"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<?> registerAdmin(@RequestBody  UserRegistrationRequest registrationForm)
			throws EmailAlreadyExistException, URISyntaxException {

		registrationService.saveUser(registrationForm, RoleType.ROLE_GUEST);
		return ResponseEntity.created(new URI(authenticationPath)).build();
	}

}