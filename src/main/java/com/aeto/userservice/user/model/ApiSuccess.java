package com.aeto.userservice.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ApiSuccess<T> implements Serializable {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;

    @JsonProperty("data")
    private T t;

    public ApiSuccess() {

    }

    public ApiSuccess(String json) {

    }

    public ApiSuccess(HttpStatus status, T t) {
        this.status = status;
        this.t = t;
        this.timestamp = LocalDateTime.now();
    }

    public ApiSuccess(HttpStatus status, LocalDateTime timestamp, T t) {
        this.status = status;
        this.timestamp = timestamp;
        this.t = t;
    }
}
