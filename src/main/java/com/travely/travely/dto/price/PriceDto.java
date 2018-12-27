package com.travely.travely.dto.price;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PriceDto {
    private long priceIdx;
    private long price;

    @Builder
    public PriceDto(long priceIdx, long price) {
        this.priceIdx = priceIdx;
        this.price = price;
    }
}
