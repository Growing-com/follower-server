package com.sarangchurch.follower.auth.domain.model;

import com.sarangchurch.follower.auth.domain.exception.ForbiddenException;
import com.sarangchurch.follower.member.command.domain.model.Gender;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import static com.sarangchurch.follower.auth.domain.model.RoleType.ADMIN;

public class LoginMember implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final String name;
    private final LocalDate birthDate;
    private final boolean earlyBorn;
    private final Gender gender;
    private final RoleType role;
    private final Long departmentId;

    @Builder
    public LoginMember(Long id, String username, String password, String name, LocalDate birthDate, boolean earlyBorn, Gender gender, RoleType role, Long departmentId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.earlyBorn = earlyBorn;
        this.gender = gender;
        this.role = role;
        this.departmentId = departmentId;
    }

    public void validateAppRole() {
        if (ADMIN.matchRole(role)) {
            throw new ForbiddenException();
        }
    }

    public void validateWebRole() {
        if (!ADMIN.matchRole(role)) {
            throw new ForbiddenException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean isEarlyBorn() {
        return earlyBorn;
    }

    public Gender getGender() {
        return gender;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public RoleType getRole() {
        return role;
    }

    public String getRoleName() {
        return "ROLE_" + role.name();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(getRoleName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
