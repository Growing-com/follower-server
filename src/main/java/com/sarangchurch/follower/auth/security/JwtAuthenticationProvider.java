package com.sarangchurch.follower.auth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtils jwtUtils;
    private final TokenUserLoader tokenUserLoader;

    public JwtAuthenticationProvider(JwtUtils jwtUtils, TokenUserLoader tokenUserLoader) {
        this.jwtUtils = jwtUtils;
        this.tokenUserLoader = tokenUserLoader;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();

        jwtUtils.validateToken(token);

        String userId = jwtUtils.extractUserId(token);
        UserDetails userDetails = tokenUserLoader.loadUserByUserId(Long.valueOf(userId));
        return new JwtAuthenticationToken(null, userDetails, true, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
