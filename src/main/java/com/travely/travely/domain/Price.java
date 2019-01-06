package com.travely.travely.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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


}
