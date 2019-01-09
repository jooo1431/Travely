package com.travely.travely.dto.baggage;

import lombok.Getter;

@Getter
public class BagImgResponseDto {
    private String bagImgUrl;

    public BagImgResponseDto(String bagImgUrl) {
        this.bagImgUrl = bagImgUrl;
    }
}
