package com.sarangchurch.follower.member.command.ui.advice;

import com.sarangchurch.follower.common.exception.ErrorResponse;
import com.sarangchurch.follower.member.command.domain.exception.IllegalMemberException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sarangchurch.follower.common.exception.ExceptionHandlerUtils.createErrorResponse;

@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(IllegalMemberException.class)
    public ResponseEntity<ErrorResponse> handleIllegalMemberNameException(IllegalMemberException e) {
        return createErrorResponse(MemberErrorCode.ILLEGAL_MEMBER, e);
    }
}
