package com.sarangchurch.follower.auth.domain;

import org.springframework.security.core.userdetails.UserDetails;

@FunctionalInterface
public interface TokenUserLoader {
    UserDetails loadUserByUserId(Long id);
}
