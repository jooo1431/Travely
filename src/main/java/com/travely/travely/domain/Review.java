package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    private long reviewIdx;
    private long storeIdx;
    private long userIdx;
    private String content;
    private long like;
    private Timestamp createAt;

    @Builder
    public Review(long reviewIdx, long storeIdx, long userIdx, String content, long like, Timestamp createAt) {
        this.reviewIdx = reviewIdx;
        this.storeIdx = storeIdx;
        this.userIdx = userIdx;
        this.content = content;
        this.like = like;
        this.createAt = createAt;
    }
}
