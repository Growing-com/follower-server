package com.sarangchurch.follower.common;

@FunctionalInterface
public interface EntitySupplier<T> {

    T toEntity();
}
