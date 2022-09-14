package com.sarangchurch.follower.admin.ui.advice;

import com.sarangchurch.follower.admin.domain.exception.ForbiddenDepartmentException;
import com.sarangchurch.follower.common.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sarangchurch.follower.admin.ui.advice.DepartmentErrorCode.FORBIDDEN_DEPARTMENT;
import static com.sarangchurch.follower.common.exception.ExceptionHandlerUtils.createErrorResponse;

@RestControllerAdvice
public class DepartmentControllerAdvice {

    @ExceptionHandler(ForbiddenDepartmentException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenDepartmentException(ForbiddenDepartmentException e) {
        return createErrorResponse(FORBIDDEN_DEPARTMENT, e);
    }
}
