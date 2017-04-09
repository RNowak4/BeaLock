package com.wpam.controllers;

import com.wpam.exceptions.UserAlreadyExistsException;
import com.wpam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String test() throws UserAlreadyExistsException {
//        userService.register("radek", "koty", "rasd@dasa.com");

        return "HEHE";
    }
}
