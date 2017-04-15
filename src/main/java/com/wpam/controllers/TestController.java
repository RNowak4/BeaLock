package com.wpam.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/test")
public class TestController {

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String test() {
        return "Test OK!";
    }
}
