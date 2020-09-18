package com.desale.visitorsManagementSystem.service.response;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

  private static final long serialVersionUID = 8317676219297719109L;

  private final String token;//immutable string

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}