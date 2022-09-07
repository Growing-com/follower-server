package com.sarangchurch.follower.auth.ui;

import com.sarangchurch.follower.auth.application.RefreshTokenService;
import com.sarangchurch.follower.auth.application.dto.TokenResponse;
import com.sarangchurch.follower.auth.ui.dto.LoginRequest;
import com.sarangchurch.follower.auth.ui.dto.TokenRefreshRequest;
import com.sarangchurch.follower.auth.utils.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManagerFactoryBean authenticationManagerFactoryBean;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManagerFactoryBean authenticationManagerFactoryBean, JwtUtils jwtUtils, RefreshTokenService refreshTokenService) {
        this.authenticationManagerFactoryBean = authenticationManagerFactoryBean;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) throws Exception {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        Authentication authentication = authenticationManagerFactoryBean.getObject().authenticate(authToken);

        String accessToken = jwtUtils.generateAccessToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String refreshToken = refreshTokenService.create(userDetails.getUsername());
        return new TokenResponse(accessToken, refreshToken);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody @Valid TokenRefreshRequest request) {
        return refreshTokenService.refresh(request.getRefreshToken());
    }

}
