package com.travely.travely.dto.inquiry;

import com.travely.travely.domain.InquiryImg;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class InquiryImgDto {
    private Long inquiryIdx;
    private String inquiryImgUrl;

    public InquiryImgDto(InquiryImg inquiryImg) {
        this.inquiryImgUrl = inquiryImg.getInquiryImg();
    }
}
