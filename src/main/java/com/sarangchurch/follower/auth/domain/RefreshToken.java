package com.sarangchurch.follower.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "refresh_token",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "token"),
                @UniqueConstraint(columnNames = "username")
        }
)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime expiryTime;
    @Column(nullable = false)
    private String username;

    protected RefreshToken() {
    }

    public RefreshToken(String token, LocalDateTime expiryTime, String username) {
        this.token = token;
        this.expiryTime = expiryTime;
        this.username = username;
    }

    public boolean isExpired(LocalDateTime at) {
        return expiryTime.isBefore(at);
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }
}
