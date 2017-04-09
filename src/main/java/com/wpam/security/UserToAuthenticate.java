package com.wpam.security;

import lombok.Getter;

@Getter
public class UserToAuthenticate {
    private final String username;
    private final String password;

    public UserToAuthenticate(String username, String password) {
        this.username = username;
        this.password = password;
    }
}