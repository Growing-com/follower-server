package com.sarangchurch.follower.auth.ui;

import com.sarangchurch.follower.auth.application.AuthService;
import com.sarangchurch.follower.auth.application.dto.LoginRequest;
import com.sarangchurch.follower.auth.application.dto.TokenRefreshRequest;
import com.sarangchurch.follower.auth.application.dto.TokenResponse;
import com.sarangchurch.follower.auth.domain.LoginMember;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        LoginMember loginMember = (LoginMember) authenticationManager.authenticate(authentication)
                .getPrincipal();
        return authService.login(loginMember);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody @Valid TokenRefreshRequest request) {
        return authService.refresh(request.getRefreshToken());
    }

}
