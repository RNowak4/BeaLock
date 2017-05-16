package com.wpam.config;

import com.wpam.domain.User;
import com.wpam.security.AuthUnauthorizedHandler;
import com.wpam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@Order(301)
public class ChildServerSecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthUnauthorizedHandler authUnauthorizedHandler;
    private UserService userService;

    @Autowired
    public ChildServerSecurityConfig(AuthUnauthorizedHandler authUnauthorizedHandler, UserService userService) {
        this.authUnauthorizedHandler = authUnauthorizedHandler;
        this.userService = userService;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(request -> {
            final String url = request.getServletPath();
            return TomcatConfig.USER_URLS.stream().noneMatch(userUrl -> url.startsWith(userUrl));
        });

        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authUnauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and().authorizeRequests()
                .antMatchers("/test/**").authenticated()
                .anyRequest().authenticated()
                .and().x509()
                .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
                .userDetailsService(userDetailsService());


        http.headers().cacheControl();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if (username.equals("Client")) {
                return new User(username, "",
                        AuthorityUtils
                                .commaSeparatedStringToAuthorityList("ROLE_USER"));
            }

            return null;
        };
    }

    @Autowired
    public void registerGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
    }
}