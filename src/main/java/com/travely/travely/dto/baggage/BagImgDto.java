package com.travely.travely.dto.baggage;

import com.travely.travely.domain.BaggageImg;
import lombok.Getter;

@Getter
public class BagImgDto {
    private String bagImgUrl;

    public BagImgDto(BaggageImg baggageImg) {
        this.bagImgUrl = baggageImg.getBagImg();
    }
}
