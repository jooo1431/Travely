package com.travely.travely.dto.review;

import com.travely.travely.domain.Review;
import lombok.Getter;

@Getter
public class ReviewUserImgResponseDto {
    private Long reviewIdx;
    private Long storeIdx;
    private Long userIdx;
    private String content;
    private Long like;
    private Long createdAt;
    private String userName;
    private String userImgUrl;

    public ReviewUserImgResponseDto(Review review) {
        this.reviewIdx = review.getReviewIdx();
        this.storeIdx = review.getStoreIdx();
        this.userIdx = review.getUserIdx();
        this.content = review.getContent();
        this.like = review.getLiked();
        this.createdAt = review.getCreateAt().getTime();
        this.userName = review.getUsers().getName();
        this.userImgUrl = review.getUsers().getProfileImg();
    }
}
