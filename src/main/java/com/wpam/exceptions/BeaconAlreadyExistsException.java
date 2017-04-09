package com.wpam.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Beacon already exist!")
public class BeaconAlreadyExistsException extends Throwable {
}