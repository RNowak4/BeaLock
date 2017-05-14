package com.wpam.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController("/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String performLogin(final Principal principal) {
        if (principal != null) {
            return "OK";
        } else {
            return "DUPA";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String performLoginPost(final Principal principal) {
        if (principal != null) {
            return "OK";
        } else {
            return "DUPA";
        }
    }
}