package com.sarangchurch.follower.common.types;

@FunctionalInterface
public interface EntitySupplier<T> {

    T toEntity();
}
