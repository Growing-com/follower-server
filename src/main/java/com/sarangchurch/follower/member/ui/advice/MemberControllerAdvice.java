package com.sarangchurch.follower.member.ui.advice;

import com.sarangchurch.follower.common.exception.ErrorResponse;
import com.sarangchurch.follower.member.domain.exception.IllegalMemberException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sarangchurch.follower.common.exception.ExceptionHandlerUtils.createErrorResponse;
import static com.sarangchurch.follower.member.ui.advice.MemberErrorCode.*;

@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(IllegalMemberException.class)
    public ResponseEntity<ErrorResponse> handleIllegalMemberNameException(IllegalMemberException e) {
        return createErrorResponse(ILLEGAL_MEMBER, e);
    }
}
