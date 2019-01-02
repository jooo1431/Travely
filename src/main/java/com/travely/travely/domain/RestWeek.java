package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Calendar;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestWeek {
    private long weekIdx;
    private long week;
    private long storeIdx;

    public void checkRest(final Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        if (calendar.DAY_OF_WEEK == this.week) throw new RuntimeException();
    }

    @Builder
    public RestWeek(long weekIdx, long week, long storeIdx) {
        this.weekIdx = weekIdx;
        this.week = week;
        this.storeIdx = storeIdx;
    }
}
