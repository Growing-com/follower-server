package com.sarangchurch.follower.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> errors;

    @Builder
    public ErrorResponse(int status, String code, String message, List<ValidationError> errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    @Getter
    static class ValidationError {
        private final String field;
        private final String reason;

        public ValidationError(String field, String message) {
            this.field = field;
            this.reason = message;
        }
    }
}
