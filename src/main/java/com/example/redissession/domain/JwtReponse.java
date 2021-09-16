package com.example.redissession.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;



public class JwtReponse extends User {
    private final Long userId;

    public JwtReponse(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userID) {
        super(username, password, authorities);
        this.userId = userID;
    }

    public Long getUserId() {
        return userId;
    }
}
