package com.desale.visitorsManagementSystem.controller;

import java.util.Objects;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desale.visitorsManagementSystem.exception.custom.AuthenticationException;
import com.desale.visitorsManagementSystem.security.AuthenticationService;
import com.desale.visitorsManagementSystem.service.response.TokenValidationResponse;
import com.desale.visitorsManagementSystem.service.response.UserResponse;
import com.desale.visitorsManagementSystem.service.request.JwtAuthenticationRequest;
import com.desale.visitorsManagementSystem.service.request.TokenValidationRequest;
import com.desale.visitorsManagementSystem.service.response.JwtAuthenticationResponse;
import com.desale.visitorsManagementSystem.util.JwtTokenUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Login Controller", description = "This Controller contains APIs for Authnticate User and generate token.")
public class JwtAuthenticationController {

	@Autowired//bean created in configuration file
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired// bean is being autowired from the implementor class(JwtUserDetailsService)
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationService authService;

	@RequestMapping(value = "${jwt.get.token.uri}", method = RequestMethod.POST)
	@ApiOperation(value = "Login to the system as admin , agent or passenger ", response = JwtAuthenticationResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully logged in and token generated"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	//ResponseEntity is an Extension of HttpEntity that adds a HttpStatus status code. Used in RestTemplate as well @Controller methods.
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest)
			throws AuthenticationException {

		authenticate(jwtAuthenticationRequest.getEmail(), jwtAuthenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	@ApiOperation(value = "validate Token", response = TokenValidationResponse.class)
	public TokenValidationResponse validateToken(@RequestBody TokenValidationRequest validateTokenRequest) {
		return authService.validateToken(validateTokenRequest);
	}

	@RequestMapping(value = "/getById/{userId}", method = RequestMethod.GET)
	public UserResponse validateToken(@PathVariable Long userId) {
		return authService.getById(userId);
	}

	/**
	 * 
	 * @param username
	 * @param password
	 */
	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);//username should not be null
		Objects.requireNonNull(password);//password should not be null

		try {
			//check authentity based on UsernamePasswordAuthenticationToken
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {//Thrown if an authentication request is rejected because the account is disabled. Makes no assertion as to whether or not the credentials were valid.
			throw new AuthenticationException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {//Thrown if an authentication request is rejected because the credentials are invalid. For this exception to be thrown, it means the account is neither locked nor disabled.
			throw new AuthenticationException("INVALID_CREDENTIALS", e);
		}
	}
}
