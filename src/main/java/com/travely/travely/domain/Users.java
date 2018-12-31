package com.travely.travely.domain;

import com.travely.travely.auth.UserDetailsImpl;
import com.travely.travely.config.CommonConfig;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Users {
    private Long userIdx;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String profileImg;
    private String auth;

    private List<Review> reviews;
    private List<Favorite> favorites;
    private List<Reserve> reserves;

    public UserDetailsImpl createUserDetails() {
        return new UserDetailsImpl(userIdx.toString(), password, AuthorityUtils.createAuthorityList(auth));
    }

    @Builder
    public Users(String email, String password, String name, String phone, String auth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.auth = auth;
    }

    public List<Review> getReviews() {
        return CommonConfig.getCheckedList(reviews);
    }

    public List<Favorite> getFavorites() {
        return CommonConfig.getCheckedList(favorites);
    }

    public List<Reserve> getReserves() {
        return CommonConfig.getCheckedList(reserves);
    }

    public Long getBagCount(){
        Reserve reserve = getReserves().stream().findFirst().orElse(null);
        if(reserve == null) return 0L;
        return reserve.getBagCount();
    }
}
