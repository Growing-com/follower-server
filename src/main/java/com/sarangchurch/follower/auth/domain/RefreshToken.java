package com.sarangchurch.follower.auth.domain;

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

    public RefreshToken(Long memberId) {
        this.memberId = memberId;
    }

    public void renew(UUID token, LocalDateTime newExpiryTime) {
        this.token = token.toString();
        expiryTime = newExpiryTime;
    }

    public boolean isExpired(LocalDateTime at) {
        return expiryTime.isBefore(at);
    }

    public String getToken() {
        return token;
    }

    public Long getMemberId() {
        return memberId;
    }
}
