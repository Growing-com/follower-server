package com.sarangchurch.follower.common.types;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Component
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainService {
}
