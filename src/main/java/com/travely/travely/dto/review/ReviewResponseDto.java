package com.travely.travely.dto.review;

import com.travely.travely.domain.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private Long reviewIdx;
    private Long storeIdx;
    private Long userIdx;
    private String content;
    private Long like;
    private Long createdAt;

    public ReviewResponseDto(Review review) {
        this.reviewIdx = review.getReviewIdx();
        this.storeIdx = review.getStoreIdx();
        this.userIdx = review.getUserIdx();
        this.content = review.getContent();
        this.like = review.getLike();
        this.createdAt = review.getCreateAt().getTime();
    }
}
