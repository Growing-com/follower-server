package com.sarangchurch.follower.auth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginRequest {
    @NotBlank
    @Size(min = 5, max = 30)
    private String username;
    @NotBlank
    @Size(min = 8, max = 30)
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
