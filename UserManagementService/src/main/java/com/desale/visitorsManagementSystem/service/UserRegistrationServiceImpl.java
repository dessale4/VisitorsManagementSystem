package com.desale.visitorsManagementSystem.service;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.desale.visitorsManagementSystem.enums.RoleType;
import com.desale.visitorsManagementSystem.exception.custom.EmailAlreadyExistException;
import com.desale.visitorsManagementSystem.model.Address;
import com.desale.visitorsManagementSystem.model.Admin;
import com.desale.visitorsManagementSystem.model.Guest;
import com.desale.visitorsManagementSystem.model.Employee;
import com.desale.visitorsManagementSystem.model.Role;
import com.desale.visitorsManagementSystem.model.User;
import com.desale.visitorsManagementSystem.repository.RoleRepository;
import com.desale.visitorsManagementSystem.repository.UserRepository;
import com.desale.visitorsManagementSystem.security.JwtUserDetails;
import com.desale.visitorsManagementSystem.service.request.UserRegistrationRequest;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * This method to add new user to the system by specific email and encoded
	 * password
	 * 
	 * @param email
	 * @param password
	 * @param roleType
	 * @throws EmailAlreadyExistException
	 */
	@Transactional // doit as a one transaction to rollback in case failure
	public void saveUser(UserRegistrationRequest registrationForm, RoleType roleType) throws EmailAlreadyExistException {
		logger.info("Start of saveUser");

		isEmailExist(registrationForm.getEmail());

		User user = null;
		switch (roleType) {
		case ROLE_EMPLOYEE:
			Address address = new Address(registrationForm.getStreet(), registrationForm.getCity(),
					registrationForm.getZipCode());
			user = new Employee(registrationForm.getEmail(), encodePassword(registrationForm.getPassword()),
					registrationForm.getFirstName(), registrationForm.getLastName(), registrationForm.getBirthDate(),
					address);
			break;
		case ROLE_GUEST:
			user = new Guest(registrationForm.getEmail(), encodePassword(registrationForm.getPassword()),
					registrationForm.getFirstName(), registrationForm.getLastName(), registrationForm.getBirthDate());
			break;

		case ROLE_ADMIN:
			user = new Admin(registrationForm.getEmail(), encodePassword(registrationForm.getPassword()),
					registrationForm.getFirstName(), registrationForm.getLastName(), registrationForm.getBirthDate());
			break;

		}
		HashSet<Role> roles = new HashSet<>();

		roles.add(roleRepo.findByName(roleType.name()));
		user.setRoles(roles);

		userRepo.save(user);

		user.setConfirmPassword(true);
		userRepo.save(user);

		logger.info("End of saveUser");

	}

	/**
	 * 
	 * @param password
	 * @return
	 */
	private String encodePassword(String password) {
		return passwordEncoder.encode(password);

	}

	/**
	 * check if email is already registered before
	 * 
	 * @param email
	 * @return
	 * @throws EmailAlreadyExistException
	 */
	private void isEmailExist(String email) throws EmailAlreadyExistException {
		logger.info("Start of isEmailExist ");

		if (userRepo.findByEmail(email) != null)
			throw new EmailAlreadyExistException("Provided Email Already Regestired !");

		logger.info("End of isEmailExist");
	}
	
}
