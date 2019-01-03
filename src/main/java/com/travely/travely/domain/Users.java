package com.travely.travely.domain;

import com.travely.travely.auth.UserDetailsImpl;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

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
    //List<String> favoriteStoreList;

   // public

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
}
