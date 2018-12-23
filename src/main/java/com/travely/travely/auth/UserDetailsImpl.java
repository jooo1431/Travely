package com.travely.travely.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserDetailsImpl extends User {

    public UserDetailsImpl(String email, List<GrantedAuthority> authorities) {
        super(email, "", authorities);
    }

    public UserDetailsImpl(String email, String password, List<GrantedAuthority> authorities) {
        super(email, password, authorities);
    }
}
