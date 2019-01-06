package com.travely.travely.dto.store;

import com.travely.travely.domain.RestWeek;
import lombok.Getter;

@Getter
public class RestWeekResponseDto {
    private long weekIdx;
    private long week;
    private long storeIdx;

    public RestWeekResponseDto(RestWeek restWeek) {
        this.weekIdx = restWeek.getWeekIdx();
        this.week = restWeek.getWeek();
        this.storeIdx = restWeek.getStoreIdx();
    }
}
