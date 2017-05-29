package com.wpam.controllers;

import com.wpam.exceptions.UserAlreadyExistsException;
import com.wpam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController(value = "/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void registerNewUser(@RequestParam("userName") final String userName,
                                @RequestParam("password") final String password,
                                @RequestParam("email") final String email)
            throws UserAlreadyExistsException {

        userService.register(userName, password, email);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public void setUserAppToken(@RequestParam("appToken") final String appToken, final Principal principal) {

        userService.setUserAppToken(appToken, principal.getName());
    }
}