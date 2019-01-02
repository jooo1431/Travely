package com.travely.travely.dto.reservation;

import com.travely.travely.domain.Price;
import lombok.Getter;

@Getter
public class PriceResponseDto {

    private Long priceIdx;
    private Long price;

    public PriceResponseDto(Price price) {
        this.priceIdx = price.getPriceIdx();
        this.price = price.getPrice();
    }
}
