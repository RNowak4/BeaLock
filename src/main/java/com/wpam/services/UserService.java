package com.wpam.services;

import com.wpam.domain.User;
import com.wpam.domain.repositories.UserRepository;
import com.wpam.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(username).get();
    }

    public void register(final String login, final String password, final String email, final String msisdn)
            throws UserAlreadyExistsException {
        final User user = new User(login, passwordEncoder.encode(password), email, msisdn);
        final Optional<User> foundUser = userRepository.findUserByLogin(login);

        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException("User with such email: " + login + " already exists!");
        }

        userRepository.save(user);
    }

    public Optional<User> getUserByLogin(final String login) {
        return userRepository.findUserByLogin(login);
    }
}
