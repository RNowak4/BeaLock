package com.wpam.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "UserEntity")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;
    private String email;
    private String msisdn;
//    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = GrantedAuthority.class, fetch = FetchType.EAGER)
    private Collection<GrantedAuthority> credentials;

    public User(String login, String encode, String email) {
        this.login = login;
        this.password = encode;
        this.email = email;
    }

    public User(String login, String encode, Collection<GrantedAuthority> credentials) {
        this.login = login;
        this.password = encode;
        this.credentials = credentials;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return credentials;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
