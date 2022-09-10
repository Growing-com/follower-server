package com.sarangchurch.follower.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthenticationToken implements Authentication {

    private final String token;
    private final UserDetails userDetails;
    private final Collection<? extends GrantedAuthority> authorities;
    private boolean isAuthenticated;

    public JwtAuthenticationToken(String token,
                                  UserDetails userDetails,
                                  boolean isAuthenticated,
                                  Collection<? extends GrantedAuthority> authorities) {

        this.token = token;
        this.userDetails = userDetails;
        this.isAuthenticated = isAuthenticated;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
