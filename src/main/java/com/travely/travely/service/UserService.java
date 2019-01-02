package com.travely.travely.service;

import com.travely.travely.auth.UserDetailsImpl;
import com.travely.travely.domain.Store;
import com.travely.travely.domain.Users;
import com.travely.travely.dto.store.StoreInfoResponseDto;
import com.travely.travely.dto.user.UsersInfoResponseDto;
import com.travely.travely.dto.user.UsersSaveRequestDto;
import com.travely.travely.exception.AlreadyExistsEmailException;
import com.travely.travely.mapper.ReservationMapper;
import com.travely.travely.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final ReservationMapper reservationMapper;

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

    public List<StoreInfoResponseDto> getLikedStoreDtos(Long userIdx, Long reserveIdx){
        List<Store> likedStores = reservationMapper.findReserveByUserIdxAndReserveIdx(userIdx,reserveIdx).stream()
                .map(reserve -> reserve.getStore()).collect(Collectors.toList());

        return likedStores.stream().map(store -> new StoreInfoResponseDto(store)).collect(Collectors.toList());
    }
}
