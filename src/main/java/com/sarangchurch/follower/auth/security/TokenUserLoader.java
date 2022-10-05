package com.sarangchurch.follower.auth.security;

import org.springframework.security.core.userdetails.UserDetails;

@FunctionalInterface
public interface TokenUserLoader {
    UserDetails loadUserByUserId(Long id);
}
