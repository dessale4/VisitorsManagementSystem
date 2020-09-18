package com.desale.visitorsManagementSystem.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.desale.visitorsManagementSystem.model.User;
import com.desale.visitorsManagementSystem.repository.UserRepository;

@Component
public class JwtUserDetailsService implements UserDetailsService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepo;
	
	//method will return an instance of a class that implements UserDetails interface
	@Override// method will be called by JwtAuthenticationController under createAuthenticationToken method
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.info("Start of loadUserByUsername");
		logger.debug("username is {} ", username);
		
		//get user from database
		User user = userRepo.findByEmail(username);

		if (user == null) {
			logger.warn("user with username : {} not found ", username);
			logger.info("End of loadUserByUsername");

			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));

		}
		logger.info("End of loadUserByUsername {} ", user.getRoles().iterator().next().getName());
		
		//JwtUserDetails implements spring UserDetails interface
		JwtUserDetails jwtUserDetails = new JwtUserDetails(user.getId(), user.getEmail(), user.getPassword(),
					user.getRoles().iterator().next().getName());
		 jwtUserDetails.setFirstName(user.getFirstName());
		 jwtUserDetails.setLastName(user.getLastName());
		 jwtUserDetails.setUserType(user.getClass().getSimpleName());
		return jwtUserDetails;
	}
	
}
