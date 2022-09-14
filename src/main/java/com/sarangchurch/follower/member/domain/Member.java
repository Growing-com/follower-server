package com.sarangchurch.follower.member.domain;

import lombok.Builder;

import javax.persistence.Column;
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

@Entity
@Table(
        name = "member",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "name")
        }
)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate birthDate;
    private boolean earlyBorn;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    @Embedded
    private MemberRole role;
    @Column(nullable = false)
    private Long departmentId;

    protected Member() {
    }

    @Builder
    public Member(String username, String password, String name, LocalDate birthDate, boolean earlyBorn, Gender gender, MemberRole role, Long departmentId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.earlyBorn = earlyBorn;
        this.gender = gender;
        this.role = role;
        this.departmentId = departmentId;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void toDepartment(Long departmentId) {
        this.departmentId = departmentId;
    }

    public boolean belongsTo(Long departmentId) {
        return this.departmentId.equals(departmentId);
    }

    public String getRoleName() {
        return role.getRole().name();
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

    public Long getDepartmentId() {
        return departmentId;
    }
}
