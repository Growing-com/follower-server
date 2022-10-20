package com.sarangchurch.follower.common.types.marker;

@FunctionalInterface
public interface EntitySupplier<T> {

    T toEntity();
}
