package com.sarangchurch.follower.member.ui.advice;

import com.sarangchurch.follower.common.exception.ErrorResponse;
import com.sarangchurch.follower.member.domain.exception.IllegalMemberNameException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sarangchurch.follower.common.exception.ExceptionHandlerUtils.createErrorResponse;
import static com.sarangchurch.follower.member.ui.advice.MemberErrorCode.*;

@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(IllegalMemberNameException.class)
    public ResponseEntity<ErrorResponse> handleIllegalMemberNameException(IllegalMemberNameException e) {
        return createErrorResponse(DUPLICATE_NAME, e);
    }
}
