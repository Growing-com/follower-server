package com.sarangchurch.follower.member.command.domain.model;

import com.sarangchurch.follower.member.command.domain.events.SmsSavedEvent;

@FunctionalInterface
public interface SmsSender {
    void send(SmsSavedEvent event);
}
