package com.wpam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/login")
    public AuthorizationTokenJson createAuthenticationToken(@RequestParam("username") String username,
                                                            @RequestParam("password") String password) throws AuthenticationException {
        String token = authenticationService.generateToken(new UserToAuthenticate(username, password));
        return new AuthorizationTokenJson(token);
    }

    static class JwtAuthenticationRequestJson {
        public String username;
        public String password;

        UserToAuthenticate asUserToAuthenticate() {
            return new UserToAuthenticate(username, password);
        }
    }

    private static class AuthorizationTokenJson {
        public String token;

        public AuthorizationTokenJson(String token) {
            this.token = token;
        }
    }
}