package com.api.parkingcontrol.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessRuleException extends RuntimeException {

    private final HttpStatus httpStatus;

    public BusinessRuleException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
