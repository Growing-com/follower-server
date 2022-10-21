package com.sarangchurch.follower.member.command.domain.repository;

import com.sarangchurch.follower.member.command.domain.model.Sms;

import java.util.Optional;

public interface SmsRepository {
    Sms save(Sms sms);

    Optional<Sms> findById(String phoneNumber);
}
