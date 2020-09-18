package com.desale.visitorsManagementSystem.config;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.desale.visitorsManagementSystem.enums.RoleType;
import com.desale.visitorsManagementSystem.exception.custom.EmailAlreadyExistException;
import com.desale.visitorsManagementSystem.model.Role;
import com.desale.visitorsManagementSystem.repository.RoleRepository;
import com.desale.visitorsManagementSystem.service.UserRegistrationServiceImpl;
import com.desale.visitorsManagementSystem.service.request.UserRegistrationRequest;

@Component
public class OnApplicationStartUp {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRegistrationServiceImpl registrationService;

	@Autowired
	private RoleRepository roleRepo;

	@EventListener
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("Start Of onApplicationEvent");
		
		fillRoleTable();
		try {
			createSuperUser();
		} catch (EmailAlreadyExistException e) {
			logger.warn("Super User Already Exist in DB");
			// eating the exception ,, this means Super user Already Created and exist in
			// DB.
		}
		logger.info("End Of onApplicationEvent");

	}

	/**
	 * @throws EmailAlreadyExistException
	 * @throws StudentAlreadyExistException
	 * 
	 */
	private void createSuperUser() throws EmailAlreadyExistException {

		UserRegistrationRequest registrationForm = new UserRegistrationRequest();
		registrationForm.setEmail("admin@gmail.com");
		registrationForm.setPassword("password");

		registrationService.saveUser(registrationForm, RoleType.ROLE_ADMIN);
	}

	/**
	 * 
	 */
	private void fillRoleTable() {
		logger.info("Start Of fillRoleTable");

		List<Role> roles = roleRepo.findAll();

		if (roles == null || roles.isEmpty()) {
			logger.debug("No Roles In a table ");
			roleRepo.save(new Role(RoleType.ROLE_EMPLOYEE.name()));
			logger.debug("Role .. ROLE_EMPLOYEE inserted");
			roleRepo.save(new Role(RoleType.ROLE_ADMIN.name()));
			logger.debug("Role .. ROLE_ADMIN inserted");
			roleRepo.save(new Role(RoleType.ROLE_GUEST.name()));
			logger.debug("Role .. ROLE_GUEST inserted");

		}
		logger.info("End Of fillRoleTable");

	}

}