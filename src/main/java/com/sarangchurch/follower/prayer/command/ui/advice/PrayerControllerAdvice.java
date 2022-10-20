package com.sarangchurch.follower.prayer.command.ui.advice;

import com.sarangchurch.follower.common.exception.ErrorResponse;
import com.sarangchurch.follower.prayer.command.domain.exception.CantLinkPrayerException;
import com.sarangchurch.follower.prayer.command.domain.exception.CommentCreateException;
import com.sarangchurch.follower.prayer.command.domain.exception.DuplicatePrayerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sarangchurch.follower.common.exception.ExceptionHandlerUtils.createErrorResponse;

@RestControllerAdvice
public class PrayerControllerAdvice {

    @ExceptionHandler(CantLinkPrayerException.class)
    public ResponseEntity<ErrorResponse> handleCantLinkPrayerException(CantLinkPrayerException e) {
        return createErrorResponse(PrayerErrorCode.CANT_LINK_PRAYER, e);
    }

    @ExceptionHandler(CommentCreateException.class)
    public ResponseEntity<ErrorResponse> handleCommentCreateException(CommentCreateException e) {
        return createErrorResponse(PrayerErrorCode.ILLEGAL_COMMENT, e);
    }

    @ExceptionHandler(DuplicatePrayerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePrayerException(DuplicatePrayerException e) {
        return createErrorResponse(PrayerErrorCode.DUPLICATE_PRAYER, e);
    }
}
