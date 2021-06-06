package com.aeto.userservice.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.logging.LogLevel;

@Getter
@Setter
@Builder
public class ResultObject {

    private String messageKey;

    private LogLevel logLevel;

    private Throwable ex;

    public ResultObject() {

    }

    public ResultObject(String messageKey, LogLevel logLevel, Throwable ex) {
        this.messageKey = messageKey;
        this.logLevel = logLevel;
        this.ex = ex;
    }
}
