package com.sarangchurch.follower.member.command.infra;

import com.sarangchurch.follower.member.command.domain.events.SmsSavedEvent;
import com.sarangchurch.follower.member.command.domain.exception.InvalidSmsException;
import com.sarangchurch.follower.member.command.domain.model.Sms;
import com.sarangchurch.follower.member.command.domain.model.SmsSender;
import com.sarangchurch.follower.member.command.domain.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.sarangchurch.follower.member.command.domain.exception.InvalidSmsException.EXPIRED_SMS;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
@RequiredArgsConstructor
@Slf4j
public class NaverSmsSender implements SmsSender {
    private final SmsRepository smsRepository;

    @Override
    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener
    public void send(SmsSavedEvent event) {
        Sms sms = smsRepository.findById(event.getPhoneNumber())
                .orElseThrow(() -> new InvalidSmsException(EXPIRED_SMS));

        log.info("send sms to {}, code {}", sms.getPhoneNumber(), sms.getCode());
    }
}
