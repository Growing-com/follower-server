package com.sarangchurch.follower.member.domain;

import com.sarangchurch.follower.auth.domain.RoleType;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "member", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "name")})
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private LocalDate birthDate;
    private Boolean earlyBorn;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Embedded
    private MemberRole role;
    private Long departmentId;
    private Favorites favorites = new Favorites();

    @Builder
    public Member(Long id, String username, String password, String name, LocalDate birthDate, Boolean earlyBorn, Gender gender, MemberRole role, Long departmentId) {
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

    public void toggleFavorite(Long toMemberId) {
        favorites.toggle(this, toMemberId);
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void toDepartment(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(getId(), member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public RoleType getRole() {
        return role.getRoleType();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Boolean isEarlyBorn() {
        return earlyBorn;
    }

    public Gender getGender() {
        return gender;
    }

    public Long getDepartmentId() {
        return departmentId;
    }
}
