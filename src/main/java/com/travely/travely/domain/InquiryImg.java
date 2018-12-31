package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryImg {
    private Long inquiryIdx;
    private MultipartFile[] inquiryImg;
}
