package com.wpam.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "User already exist!")
public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String s) {
        super(s);
    }
}
