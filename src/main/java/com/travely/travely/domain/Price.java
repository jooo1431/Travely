package com.travely.travely.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Price {
    private Long priceIdx;
    private Long price;

    public Long compareHour(final Long hour, final Long price) {
        if (this.priceIdx < hour)
            return this.price;
        return price;
    }

    public Long getDiffHour(final Long hour) {
        Long temp = hour - this.getPriceIdx() + 1;
        if (hour - this.getPriceIdx() % 12 == 0) temp--;
        return temp;
    }
}
