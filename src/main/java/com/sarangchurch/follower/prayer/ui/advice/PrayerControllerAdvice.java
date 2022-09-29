package com.sarangchurch.follower.prayer.ui.advice;

import com.sarangchurch.follower.common.exception.ErrorResponse;
import com.sarangchurch.follower.prayer.domain.exception.CantLinkPrayerException;
import com.sarangchurch.follower.prayer.domain.exception.DuplicatePrayerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sarangchurch.follower.common.exception.ExceptionHandlerUtils.createErrorResponse;
import static com.sarangchurch.follower.prayer.ui.advice.PrayerErrorCode.CANT_LINK_PRAYER;
import static com.sarangchurch.follower.prayer.ui.advice.PrayerErrorCode.DUPLICATE_PRAYER;

@RestControllerAdvice
public class PrayerControllerAdvice {

    @ExceptionHandler(CantLinkPrayerException.class)
    public ResponseEntity<ErrorResponse> handleCantLinkPrayerException(CantLinkPrayerException e) {
        return createErrorResponse(CANT_LINK_PRAYER, e);
    }

    @ExceptionHandler(DuplicatePrayerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePrayerException(DuplicatePrayerException e) {
        return createErrorResponse(DUPLICATE_PRAYER, e);
    }
}
