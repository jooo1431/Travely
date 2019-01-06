package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
    private Long userIdx;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String profileImg;
    private String auth;

    @Builder
    public Profile(Long userIdx, String email, String password, String name, String phone, String profileImg, String auth) {
        this.userIdx = userIdx;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.profileImg = profileImg;
        this.auth = auth;
    }
}
