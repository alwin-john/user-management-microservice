package com.aeto.userservice.user.exception;

import com.aeto.userservice.user.utility.ResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ResponseBuilder responseBuilder;

    @ExceptionHandler(BusinessErrorException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
        BusinessErrorException ex) {
        return responseBuilder.build(ex.getResultObject());
    }

}
