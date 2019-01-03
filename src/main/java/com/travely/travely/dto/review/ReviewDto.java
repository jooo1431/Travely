package com.travely.travely.dto.review;

import com.travely.travely.domain.Review;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDto {
    private Long reviewIdx;
    private Long storeIdx;
    private Long userIdx;
    private String content;
    private Long like;
    private Date createAt;

    public ReviewDto(Review review) {
        this.reviewIdx = review.getReviewIdx();
        this.storeIdx = review.getStoreIdx();
        this.userIdx = getReviewIdx();
        this.content = review.getContent();
        this.like = review.getLike();
        this.createAt = review.getCreateAt();
    }

    public boolean checkProperties(){
        return ( this.storeIdx != null & this.content != null & this.like != null & this.createAt != null );
    }

    public Review toEntity(){
        return Review.builder()
                .reviewIdx(reviewIdx)
                .storeIdx(storeIdx)
                .content(content)
                .like(like)
                .createAt(createAt)
                .build();
    }
}
