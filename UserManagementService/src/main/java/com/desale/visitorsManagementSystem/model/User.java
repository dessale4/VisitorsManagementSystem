package com.desale.visitorsManagementSystem.model;


import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "user")
public class User {
	private Long id;//entity is set as get by property
	private String email;
	private String password;
	private boolean confirmPassword;
	private Date registeredAt;
	private Set<Role> roles;
	private String firstName;
	private String lastName;
	private Date birthDate;

	public User() {

	}

	public User(String email, String password, String firstName, String lastName, Date birthDate) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "email can not be empty")
	@Email(message = " Please provide valid Email")
	@ApiModelProperty(notes = " User Email ")
	public String getEmail() {
		return email;
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@NotEmpty(message = "password can not be empty")
//	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!^*-_]).{8,30})", message = " password must be at least 8 characters, at most 30 , contains 1 uppercase character"
//			+ ", contains 1 lowercase character , contains 1 special character from @#$%!^*-_" + "and 1 number")
	@ApiModelProperty(notes = " password must be at least 8 characters, at most 30 , contains 1 uppercase character"
			+ ", contains 1 lowercase character , contains 1 special character from @#$%!^*-_" + "and 1 number")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
//	@NotEmpty(message = "confirm password cannot be empty")
	@ApiModelProperty(notes = "Must Match Password")
//	@AssertTrue(message = "confirm password field should be equal to password field")
	@JsonIgnore//indicates that the annotated method or field is to be ignored by introspection-based serialization and deserialization functionality
	public boolean isConfirmPassword() {
		return this.password.equals(this.confirmPassword);
	}

	public void setConfirmPassword(boolean confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	

	@CreationTimestamp //The property value will be set to the current VM date exactly once when saving the owning entity for the first time
	public Date getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(Date registeredAt) {
		this.registeredAt = registeredAt;
	}

//	@PrePersist
	public void onRegisteriation() {
		this.registeredAt = new Date();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", enabled=" + confirmPassword
				+ ", registeredAt=" + registeredAt + ", roles=" + roles + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}