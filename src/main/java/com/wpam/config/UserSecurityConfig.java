package com.wpam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Configuration
@EnableWebSecurity
@Order(302)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .formLogin()
//                    .loginPage("/logina")
                .usernameParameter("login")
                .passwordParameter("password")
//                    .permitAll()
                .and().logout().permitAll().logoutUrl("/logout")
                .and().authorizeRequests()
                .antMatchers("/user/**").permitAll();
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
//                    final User user = userRepository.findUserByLogin(login);
//                    if (user == null || !passwordEncoder.matches(rawPassword, user.password)) {
//                        throw new BadCredentialsException("Invalid credentials");
//                    }

                List<GrantedAuthority> authorities = null;

                return new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), authorities);
            }
        });
    }
}