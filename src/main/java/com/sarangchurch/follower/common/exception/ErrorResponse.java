package com.sarangchurch.follower.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> errors;

    public ErrorResponse(int status, String code, String message) {
        this(status, code, message, null);
    }

    @Builder
    public ErrorResponse(int status, String code, String message, List<ValidationError> errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    static class ValidationError {
        private final String field;
        private final String reason;

        public ValidationError(String field, String message) {
            this.field = field;
            this.reason = message;
        }

        public String getField() {
            return field;
        }

        public String getReason() {
            return reason;
        }
    }
}
