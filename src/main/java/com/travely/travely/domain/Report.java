package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {
    private Long reportIdx;
    private Long reviewIdx;
    private Long userIdx;

    @Builder
    public Report(Long reportIdx, Long reviewIdx, Long userIdx) {
        this.reportIdx = reportIdx;
        this.reviewIdx = reviewIdx;
        this.userIdx = userIdx;
    }
}
