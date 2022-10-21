package com.sarangchurch.follower.member.command.infra;

import com.sarangchurch.follower.member.command.domain.model.Sms;
import com.sarangchurch.follower.member.command.domain.repository.SmsRepository;
import org.springframework.data.repository.CrudRepository;

public interface RedisSmsRepository extends CrudRepository<Sms, String>, SmsRepository {
}
