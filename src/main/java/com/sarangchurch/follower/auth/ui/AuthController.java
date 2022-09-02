package com.sarangchurch.follower.auth.ui;

import com.sarangchurch.follower.auth.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public AuthController(AuthenticationManagerFactoryBean authenticationManagerFactoryBean, JwtUtils jwtUtils) {
        this.authenticationManagerFactoryBean = authenticationManagerFactoryBean;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/token")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) throws Exception {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authentication = authenticationManagerFactoryBean.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateJwtToken(authentication);
        return new TokenResponse(accessToken);
    }
}
