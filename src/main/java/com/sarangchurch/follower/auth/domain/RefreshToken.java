package com.sarangchurch.follower.auth.domain;

import com.sarangchurch.follower.auth.domain.exception.RefreshTokenExpiredException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "refresh_token",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "token"),
                @UniqueConstraint(columnNames = "memberId")
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
    private Long memberId;

    protected RefreshToken() {
    }

    public RefreshToken(UUID token, LocalDateTime expiryTime, Long memberId) {
        this.token = token.toString();
        this.expiryTime = expiryTime;
        this.memberId = memberId;
    }

    public void renew(UUID token, LocalDateTime newExpiryTime) {
        this.token = token.toString();
        expiryTime = newExpiryTime;
    }

    public void validateExpiration(LocalDateTime at) {
        if (expiryTime.isBefore(at)) {
            throw new RefreshTokenExpiredException();
        }
    }

    public String getToken() {
        return token;
    }

    public Long getMemberId() {
        return memberId;
    }
}
