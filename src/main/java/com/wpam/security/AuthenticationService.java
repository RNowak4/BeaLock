package com.wpam.security;

import com.wpam.domain.User;
import com.wpam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    public String generateToken(UserToAuthenticate userToAuthenticate) {
        final Authentication authentication = authentication(userToAuthenticate);
        authenticate(authentication);
        return generateTokenForUser(userToAuthenticate);
    }

    private Authentication authentication(UserToAuthenticate userToAuthenticate) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userToAuthenticate.getUsername(),
                userToAuthenticate.getPassword()));
    }

    private void authenticate(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String generateTokenForUser(UserToAuthenticate userToAuthenticate) {
        User user = userService.loadUserByUsername(userToAuthenticate.getUsername());
        return jwtTokenUtil.generateToken(user);
    }
}
