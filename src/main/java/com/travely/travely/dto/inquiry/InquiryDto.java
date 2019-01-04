package com.travely.travely.dto.inquiry;

import com.travely.travely.domain.Inquiry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InquiryDto {
    private String content;
    private Date createAt;

    private List<String> inquiryImgs;

    public Inquiry toEntity() {
        return Inquiry.builder()
//                .userIdx(userIdx)
                .content(content)
                .createAt(createAt)
                .build();
    }

    public boolean checkProperties() {
        return this.content != null;
    }
}
