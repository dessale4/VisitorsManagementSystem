package com.desale.visitorsManagementSystem.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

	public Admin() {
		super();
	}

	public Admin(String email, String password, String firstName, String lastName, Date birthDate) {
		super(email, password, firstName, lastName, birthDate);
	}

}
