package com.desale.visitorsManagementSystem.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("guest")
public class Guest extends User {

	public Guest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Guest(String email, String password, String firstName, String lastName, Date birthDate) {
		super(email, password, firstName, lastName, birthDate);
	}

}
