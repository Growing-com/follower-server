package com.sarangchurch.follower.common.types;

import javax.persistence.Embeddable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Embeddable
@Target(TYPE)
@Retention(RUNTIME)
public @interface ValueObject {
}
