package com.travely.travely.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Price {
    private Long priceIdx;
    private Long price;

    public Long getMillsecToHour(final Long start, final Long end) {
        Long hour = (end - start) / 1000 / 60 / 60;
        if (hour * 1000 * 60 * 60 != end - start)
            hour++;
        return hour;
    }

    public Long findPriceIdxByUnit(final List<Price> prices, final Long unit) {
        for (Price price : prices) {
            if (price.getPrice() == unit) return price.getPriceIdx();
        }
        throw new RuntimeException();
    }

    public Long getPriceUnit(final List<Price> prices, final Long hour) {
        Long unit = 0L;
        for (int i = 0; i < prices.size(); i++) {
            unit = prices.get(i).compareHour(hour, unit);
        }
        return unit;
    }

    public Long compareHour(final Long hour, final Long price) {
        if (this.priceIdx < hour)
            return this.price;
        return price;
    }

    public Long getExtraChargeCount(final Long hour, final Long finalIdx) {
        Long extra = 0L;
        if (hour > finalIdx) {
            final Long temp = hour - finalIdx;
            extra = temp / 12;
            if (temp % 12 == 0) extra--;
        }
        return extra;
    }

}
