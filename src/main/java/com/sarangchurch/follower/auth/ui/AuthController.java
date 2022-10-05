package com.sarangchurch.follower.auth.ui;

import com.sarangchurch.follower.auth.application.AuthService;
import com.sarangchurch.follower.auth.ui.dto.LoginRequest;
import com.sarangchurch.follower.auth.ui.dto.TokenRefreshRequest;
import com.sarangchurch.follower.auth.application.dto.TokenResponse;
import com.sarangchurch.follower.auth.domain.model.LoginMember;
import com.sarangchurch.follower.common.types.ApiResponse;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping("/loginApp")
    public ApiResponse<TokenResponse> loginApp(@RequestBody @Valid LoginRequest request) {
        return ApiResponse.of(authService.loginApp(authenticateRequest(request)));
    }

    @PostMapping("/loginWeb")
    public ApiResponse<TokenResponse> loginWeb(@RequestBody @Valid LoginRequest request) {
        return ApiResponse.of(authService.loginWeb(authenticateRequest(request)));
    }

    private LoginMember authenticateRequest(LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        return (LoginMember) authenticationManager.authenticate(authentication)
                .getPrincipal();
    }

    @PostMapping("/refresh")
    public ApiResponse<TokenResponse> refresh(@RequestBody @Valid TokenRefreshRequest request) {
        return ApiResponse.of(authService.refresh(request.getRefreshToken()));
    }

}
