package com.travely.travely.auth.ajax;

import com.travely.travely.domain.Users;
import com.travely.travely.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AjaxUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Users users = userMapper.findByEmail(email);
        if (users == null) {
            throw new UsernameNotFoundException(email + "라는 사용자가 없습니다.");
        }

        return users.createUserDetails();
    }
}
