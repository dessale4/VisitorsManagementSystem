package com.desale.visitorsManagementSystem.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("employee")
public class Employee extends User {
	@Embedded
	private Address address;

	public Employee() {
		super();
	}

	public Employee(String email, String password, String firstName, String lastName, Date birthDate,
			Address address) {
		super(email, password, firstName, lastName, birthDate);
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
