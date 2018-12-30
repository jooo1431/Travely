package com.travely.travely.dto.inquiry;

import com.travely.travely.domain.Inquiry;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@NoArgsConstructor
public class InquiryDto {
//    private Long userIdx;
    private String content;
    private Date createAt;

    public Inquiry toEntity(){
        return Inquiry.builder()
//                .userIdx(userIdx)
                .content(content)
                .createAt(createAt)
                .build();
    }

    public boolean checkProperties(){
        return this.content != null;
    }
}
