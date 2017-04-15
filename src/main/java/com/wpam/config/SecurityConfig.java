package com.wpam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ChildServerSecurityConfig.class, UserSecurityConfig.class})
public class SecurityConfig {
}