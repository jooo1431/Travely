package com.travely.travely.dto.user;

import com.travely.travely.domain.Users;
import com.travely.travely.dto.store.StoreInfoResponseDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UsersInfoResponseDto {
    private Long userIdx;
    private String email;
    private String name;
    private String phone;
    private String profileImg;
    private Long reviewCount;
    private Long favoriteCount;
    private Long myBagCount;
    private List<StoreInfoResponseDto> storeInfoResponseDtoList;

    public UsersInfoResponseDto(Users users) {
        this.userIdx = users.getUserIdx();
        this.email = users.getEmail();
        this.name = users.getName();
        this.phone = users.getPhone();
        this.profileImg = users.getProfileImg();
        this.reviewCount = users.getReviews().stream().count();
        this.favoriteCount = users.getFavorites().stream().count();
        this.myBagCount = users.getBagCount();
        this.storeInfoResponseDtoList = users.getReserves().stream()
                .map(reserve -> new StoreInfoResponseDto(reserve.getStore()))
                .collect(Collectors.toList());
    }
}
