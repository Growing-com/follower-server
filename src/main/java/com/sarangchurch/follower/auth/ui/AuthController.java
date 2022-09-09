package com.sarangchurch.follower.auth.ui;

import com.sarangchurch.follower.auth.application.AuthService;
import com.sarangchurch.follower.auth.application.dto.LoginRequest;
import com.sarangchurch.follower.auth.application.dto.TokenRefreshRequest;
import com.sarangchurch.follower.auth.application.dto.TokenResponse;
import com.sarangchurch.follower.auth.domain.LoginMember;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManagerFactoryBean authenticationManagerFactoryBean;
    private final AuthService authService;

    public AuthController(AuthenticationManagerFactoryBean authenticationManagerFactoryBean, AuthService authService) {
        this.authenticationManagerFactoryBean = authenticationManagerFactoryBean;
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        LoginMember loginMember = (LoginMember) authenticationManagerFactoryBean.getObject()
                .authenticate(authentication)
                .getPrincipal();
        return authService.login(loginMember);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody @Valid TokenRefreshRequest request) {
        return authService.refresh(request.getRefreshToken());
    }

}
