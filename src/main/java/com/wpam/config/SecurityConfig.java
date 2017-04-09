package com.wpam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    @Configuration
    @Order(1)
    public static class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.
                    csrf().disable()
                    .formLogin()
//                    .loginPage("/logina")
                    .usernameParameter("login")
                    .passwordParameter("password")
//                    .permitAll()
                    .and()
                    .logout()
                    .permitAll()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/user/**").permitAll()
                    .antMatchers("/beacon/**").authenticated();
        }

    }

    @Configuration
    @Order(2)
    public static class ApiSecurityConfig2 extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.
                    csrf().disable()
                    .formLogin()
//                    .loginPage("/logina")
                    .usernameParameter("login")
                    .passwordParameter("password")
//                    .permitAll()
                    .and()
                    .logout()
                    .permitAll()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/lock/**").permitAll()
                    .antMatchers("/test/**").authenticated()
                    .anyRequest().authenticated()
                    .and().x509()
                    .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
                    .userDetailsService(userDetailsService());
        }

    }

//    @Configuration
//    @Order(2)
//    public static class RootSecurityConfig extends WebSecurityConfigurerAdapter {
//        private AuthUnauthorizedHandler authUnauthorizedHandler;
//        private final JwtAuthenticationTokenFilter authenticationTokenFilter;
//        private UserService userService;
//
//        @Autowired
//        public RootSecurityConfig(AuthUnauthorizedHandler authUnauthorizedHandler, JwtAuthenticationTokenFilter authenticationTokenFilter, UserService userService) {
//            this.authUnauthorizedHandler = authUnauthorizedHandler;
//            this.authenticationTokenFilter = authenticationTokenFilter;
//            this.userService = userService;
//        }
//
//        @Autowired
//        public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//            authenticationManagerBuilder
//                    .userDetailsService(userService)
//                    .passwordEncoder(new BCryptPasswordEncoder());
//        }
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .csrf().disable()
//                    .exceptionHandling().authenticationEntryPoint(authUnauthorizedHandler).and()
//                    .sessionManagement().sessionCreationPolicy(STATELESS).and()
//                    .authorizeRequests()
//                    .antMatchers("/user").authenticated()
//                    .antMatchers("/test").authenticated()
//                    .anyRequest().permitAll()
//                    .and().x509()
//                    .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
//                    .userDetailsService(userDetailsService());
//
//
//            http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//            http.headers().cacheControl();
//        }
//
//        @Bean
//        public UserDetailsService userDetailsService() {
//            return username -> {
//                if (username.equals("cid")) {
//                    return new User(username, "",
//                            AuthorityUtils
//                                    .commaSeparatedStringToAuthorityList("ROLE_USER"));
//                }
//
//                return null;
//            };
//        }
//
//        @Autowired
//        public void registerGlobal(AuthenticationManagerBuilder auth) throws Exception {
//            auth
//                    .inMemoryAuthentication()
//                    .withUser("user").password("password").roles("USER").and()
//                    .withUser("admin").password("password").roles("USER", "ADMIN");
//        }
//    }
}