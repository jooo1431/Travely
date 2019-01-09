package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry {
    private Long inquiryIdx;
    private Long userIdx;
    private String content;
    private Timestamp createAt;

    @Builder
    public Inquiry(Long inquiryIdx, Long userIdx, String content, Timestamp createAt) {
        this.inquiryIdx = inquiryIdx;
        this.userIdx = userIdx;
        this.content = content;
        this.createAt = createAt;
    }
}
