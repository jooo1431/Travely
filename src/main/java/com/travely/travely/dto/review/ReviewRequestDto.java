package com.travely.travely.dto.review;

import com.travely.travely.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    private Long storeIdx;
    private String content;
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
