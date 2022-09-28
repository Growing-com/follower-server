package com.sarangchurch.follower.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.sarangchurch.follower.common.exception.ErrorResponse.*;
import static java.util.Collections.emptyList;

@Slf4j
public class ExceptionHandlerUtils {

    private ExceptionHandlerUtils() {
    }

    public static ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode, Exception e) {
        log.error("Error: ", e);
        return createErrorResponse(errorCode, e, emptyList());
    }

    public static ResponseEntity<ErrorResponse> createErrorResponse(
            ErrorCode errorCode,
            Exception e,
            List<ValidationError> validationErrors
    ) {

        ErrorResponse errorResponse = builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(e.getMessage())
                .errors(validationErrors)
                .build();

        return ResponseEntity.status(errorCode.getStatus())
                .body(errorResponse);
    }
}
