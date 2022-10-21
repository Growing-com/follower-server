package com.sarangchurch.follower.member.command.domain.events;

import lombok.Getter;

@Getter
public class SmsSavedEvent {
    private final String phoneNumber;
    private final int code;

    public SmsSavedEvent(String phoneNumber, int code) {
        this.phoneNumber = phoneNumber;
        this.code = code;
    }
}
