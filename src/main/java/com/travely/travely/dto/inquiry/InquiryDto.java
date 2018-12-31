package com.travely.travely.dto.inquiry;

import com.travely.travely.domain.Inquiry;
import com.travely.travely.domain.InquiryImg;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Setter
@NoArgsConstructor
public class InquiryDto {
    private String content;
    private Date createAt;

    private MultipartFile[] inquiryImgs;

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
