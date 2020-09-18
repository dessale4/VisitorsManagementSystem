package com.desale.visitorsManagementSystem.service.request;
import java.io.Serializable;

public class  JwtAuthenticationRequest implements Serializable {
  
  private static final long serialVersionUID = -5616176897013108345L;

  	private String email;
    private String password;
    // non parameterized constructor
    public JwtAuthenticationRequest() {
        super();
    }
    
    //parameterized constructor
    public JwtAuthenticationRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

  
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

