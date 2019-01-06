package com.travely.travely.dto.report;

import com.travely.travely.domain.Report;
import lombok.AllArgsConstructor;
import lombok.Setter;


@Setter
@AllArgsConstructor
public class ReportResponseDto {
    private Long reportIdx;
    private Long reviewIdx;
    private Long userIdx;


    public ReportResponseDto(Long reviewIdx, Long userIdx) {
        this.reviewIdx = reviewIdx;
        this.userIdx = userIdx;
    }

    public Report toEntity(){
        return Report.builder()
                .reportIdx(this.reportIdx)
                .reviewIdx(this.reviewIdx)
                .userIdx(this.userIdx)
                .build();
    }
}
