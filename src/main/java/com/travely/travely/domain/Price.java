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

    public Long getDiffHour(final Long hour) {
        Long temp = hour - this.getPriceIdx() + 1;
        if (hour - this.getPriceIdx() % 12 == 0) temp--;
        return temp;
    }

    public Long findPriceIdxByUnit(final List<Price> prices, final Long unit) {
        for (Price price : prices) {
            if (price.getPrice() == unit) return price.getPriceIdx();
        }
        throw new RuntimeException();
    }

    public Long getPriceUnit(final List<Price> prices, final Long hour) {
        Long unit = 0L;
        for (int i = 0; i < prices.size() - 1; i++) {
            unit = prices.get(i).compareHour(hour, unit);
        }
        return unit;
    }

    public Long compareHour(final Long hour, final Long price) {
        if (this.priceIdx < hour)
            return this.price;
        return price;
    }


}
