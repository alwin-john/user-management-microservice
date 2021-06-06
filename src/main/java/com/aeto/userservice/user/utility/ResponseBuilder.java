package com.aeto.userservice.user.utility;

import com.aeto.userservice.user.model.ApiError;
import com.aeto.userservice.user.model.ApiSuccess;
import com.aeto.userservice.user.model.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;

@Component
@Slf4j
public class ResponseBuilder {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    public ResponseEntity<Object> build(ResultObject resultObject) {
        ApiError apiError = ApiError.builder().status(HttpStatus.UNPROCESSABLE_ENTITY)
            .timestamp(LocalDateTime.now()).message(getMessage(resultObject.getMessageKey())).debugMessage("").build();
        printLog(resultObject, apiError);
        return new ResponseEntity<>(apiError, apiError.getStatus());

    }

    public ResponseEntity<Object> build(ApiSuccess apiSuccess) {
        return new ResponseEntity<>(apiSuccess, apiSuccess.getStatus());
    }

    private void printLog(ResultObject resultObject, ApiError apiError) {
        switch (resultObject.getLogLevel()) {
            case DEBUG:
                log.debug("Business Exception", apiError.toString());
                break;
            case ERROR:
                log.error("Business Exception", apiError.toString());
                break;
            default:
                log.info("Business Exception", apiError.toString());
        }
    }

    private String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }
}
