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
    private long liked;
    private Timestamp createAt;

    private Users users;

    @Builder
    public Review(long storeIdx, long userIdx, String content, long liked) {
        this.storeIdx = storeIdx;
        this.userIdx = userIdx;
        this.content = content;
        this.liked = liked;
    }
}
