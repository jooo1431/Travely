package com.travely.travely.service;

import com.travely.travely.auth.UserDetailsImpl;
import com.travely.travely.domain.Users;
import com.travely.travely.dto.user.UsersInfoResponseDto;
import com.travely.travely.dto.user.UsersSaveRequestDto;
import com.travely.travely.exception.AlreadyExistsEmailException;
import com.travely.travely.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    @Transactional
    public UserDetailsImpl saveUsers(UsersSaveRequestDto usersSaveRequestDto) {
        if (isExistsByEmail(usersSaveRequestDto.getEmail())) throw new AlreadyExistsEmailException();

        Users users = usersSaveRequestDto.toEntity(passwordEncoder);
        userMapper.saveUsers(users);

        return users.createUserDetails();
    }

    public Boolean isExistsByEmail(String email) {
        return userMapper.findByEmail(email) != null;
    }

    public UsersInfoResponseDto getMyProfile(Long userIdx) {
        Users users = userMapper.findUserByUserIdx(userIdx);
        return new UsersInfoResponseDto(users);
    }
}
