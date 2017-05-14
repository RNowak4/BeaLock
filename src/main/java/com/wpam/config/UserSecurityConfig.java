package com.wpam.config;

import com.wpam.domain.User;
import com.wpam.domain.repositories.UserRepository;
import com.wpam.security.AuthFailureHandler;
import com.wpam.security.AuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(302)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;
    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private AuthSuccessHandler authSuccessHandler = new AuthSuccessHandler();
    private AuthFailureHandler authFailureHandler = new AuthFailureHandler();

    @Autowired
    public UserSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .formLogin().permitAll().loginPage("/login")
                .usernameParameter("login").passwordParameter("password")
                .successHandler(authSuccessHandler).failureHandler(authFailureHandler)
                .and().logout().permitAll().logoutUrl("/logout")
                .and().authorizeRequests()
                .antMatchers("/user/**").permitAll()
                .anyRequest().authenticated();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(new AuthenticationProvider() {

            @Override
            public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

                final String login = String.valueOf(token.getPrincipal());
                final String rawPassword = String.valueOf(token.getCredentials());
                final Optional<User> user = userRepository.findUserByLogin(login);
                if (!user.isPresent() || !passwordEncoder.matches(rawPassword, user.get().getPassword())) {
                    throw new BadCredentialsException("Invalid credentials");
                }

                final Collection<? extends GrantedAuthority> authorities = user.get().getAuthorities();
                return new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), authorities);
            }
        });
    }
}