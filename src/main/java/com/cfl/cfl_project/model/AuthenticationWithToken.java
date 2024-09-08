package com.cfl.cfl_project.model;


import org.springframework.security.core.Authentication;

public class AuthenticationWithToken {
    private Authentication authentication;
    private String token;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthenticationWithToken() {

    }

    public AuthenticationWithToken(Authentication authentication, String token) {
        this.authentication = authentication;
        this.token = token;
    }
}
