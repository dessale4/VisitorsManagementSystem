package com.desale.visitorsManagementSystem.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.desale.visitorsManagementSystem.repository.UserRepository;
import com.desale.visitorsManagementSystem.service.response.TokenValidationResponse;
import com.desale.visitorsManagementSystem.service.response.UserResponse;
import com.desale.visitorsManagementSystem.service.request.TokenValidationRequest;
import com.desale.visitorsManagementSystem.model.User;
import com.desale.visitorsManagementSystem.util.JwtTokenUtil;

@Service
public class AuthenticationService {

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserRepository userRepository;

	public TokenValidationResponse validateToken(TokenValidationRequest validateTokenRequest) {

		String username = null;
		TokenValidationResponse tokenValidationResponse = new TokenValidationResponse();

		try {
			username = jwtTokenUtil.getUsernameFromToken(validateTokenRequest.getToken());

			if (username != null) {

				UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

				if (jwtTokenUtil.validateToken(validateTokenRequest.getToken(), userDetails)) {
					return constructTokenResponse((JwtUserDetails) userDetails, tokenValidationResponse);
				}
			}
		} catch (Exception e) {
			tokenValidationResponse.setValid(false);

		}
		return tokenValidationResponse;
	}

	private TokenValidationResponse constructTokenResponse(JwtUserDetails userDetails,
			TokenValidationResponse tokenValidationResponse) {

		tokenValidationResponse.setValid(true);
		tokenValidationResponse.setId(userDetails.getId());
		tokenValidationResponse.setUsername(userDetails.getUsername());
		tokenValidationResponse.setFirstName(userDetails.getFirstName());
		tokenValidationResponse.setLastName(userDetails.getLastName());
		tokenValidationResponse.setUserType(userDetails.getUserType());
		List<String> authorities = new ArrayList<String>();

		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			authorities.add(authority.getAuthority());
		}

		tokenValidationResponse.setAuthorites(authorities);
		return tokenValidationResponse;
	}

	public UserResponse getById(Long id) {

		UserResponse userDto = new UserResponse();

		User user = null;

		user = userRepository.getOne(id);

		try {
			user.isConfirmPassword();
		} catch (Exception e) {
			userDto.setExist(false);
			return userDto;
		}
		userDto.setExist(true);

		userDto.setFirstName(user.getFirstName());

		userDto.setListName(user.getLastName());

		return userDto;
	}
}
