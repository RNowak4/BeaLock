package com.wpam.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Beacon doesn't exist!")
public class NoSuchBeaconException extends Exception {
}
