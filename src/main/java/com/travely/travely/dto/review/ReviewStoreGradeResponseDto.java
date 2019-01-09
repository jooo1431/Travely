package com.travely.travely.dto.review;

import com.travely.travely.dto.store.StoreGradeReview;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewStoreGradeResponseDto {
    private List<ReviewUserImgResponseDto> reviewUserImgResponseDtos;
    private StoreGradeReview storeGradeReview;

    public ReviewStoreGradeResponseDto(List<ReviewUserImgResponseDto> reviewUserImgResponseDtos, StoreGradeReview storeGradeReview) {
        this.reviewUserImgResponseDtos = reviewUserImgResponseDtos;
        this.storeGradeReview = storeGradeReview;
    }
}
