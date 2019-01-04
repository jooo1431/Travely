package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryImg {
    private Long inquiryIdx;
    private String inquiryImg;
}
