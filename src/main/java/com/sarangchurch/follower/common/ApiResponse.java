package com.sarangchurch.follower.common;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final T content;

    public static <T> ApiResponse<T> of(T content) {
        return new ApiResponse<>(content);
    }

    private ApiResponse(T content) {
        this.content = content;
    }
}
