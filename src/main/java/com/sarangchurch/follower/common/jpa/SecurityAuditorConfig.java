package com.sarangchurch.follower.common.jpa;

import com.sarangchurch.follower.auth.domain.model.LoginMember;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class SecurityAuditorConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return Optional.empty();
            }

            LoginMember loginMember = (LoginMember) authentication.getPrincipal();
            return Optional.of(loginMember.getName());
        };
    }
}
