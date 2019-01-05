package com.travely.travely.dto.inquiry;

import com.travely.travely.domain.Inquiry;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InquiryDto {

    @ApiModelProperty(example = "문의사항입니다", position = 3)
    private String content;
    @ApiModelProperty(example = "1546227587000", position = 4)
    private Long createAt;
    private List<String> inquiryImgs;

    public Inquiry toEntity() {
        return Inquiry.builder()
                .content(this.content)
                .createAt(new Timestamp(this.createAt))
                .build();
    }

    public boolean checkProperties() {
        return this.content != null;
    }
}
