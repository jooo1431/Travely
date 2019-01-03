package com.travely.travely.dto.review;

import com.travely.travely.domain.Review;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    @ApiModelProperty(example = "1", position = 1)
    private Long storeIdx;
    @ApiModelProperty(example = "리뷰의 내용을 적어", position = 2)
    private String content;
    @ApiModelProperty(example = "3", position = 3)
    private Long liked;

    public void checkLiked() {
        if (this.liked > 5) throw new RuntimeException();
        if (this.liked < 0) throw new RuntimeException();
    }

    public Review toEntity(final Long userIdx) {
        return Review.builder()
                .content(this.content)
                .liked(this.liked)
                .storeIdx(this.storeIdx)
                .userIdx(userIdx)
                .build();
    }
}
