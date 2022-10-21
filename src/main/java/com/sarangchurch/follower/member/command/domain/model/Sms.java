package com.sarangchurch.follower.member.command.domain.model;

import com.sarangchurch.follower.common.types.vo.PhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Random;

import static lombok.AccessLevel.PROTECTED;

@RedisHash(value = "sms", timeToLive = 300L)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Sms implements Serializable {

    private static final long serialVersionUID = -4439114469417994311L;

    private static final Random RANDOM = new Random();
    private static final int MIN_CODE = 1000;

    @Id
    private String phoneNumber;
    private int code;
    private boolean verified;

    public Sms(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber.getPhoneNumber();
        this.code = generateCode();
        this.verified = false;
    }

    private int generateCode() {
        return MIN_CODE + RANDOM.nextInt(9000);
    }

    public boolean matchCode(int code) {
        return this.code == code;
    }

    public void verify() {
        verified = true;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", code=" + code +
                ", verified=" + verified +
                '}';
    }
}
