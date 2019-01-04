package com.travely.travely.service;

import com.travely.travely.domain.Review;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.review.ReviewRequestDto;
import com.travely.travely.dto.review.ReviewResponseDto;
import com.travely.travely.dto.review.ReviewStoreResponseDto;
import com.travely.travely.mapper.ReviewMapper;
import com.travely.travely.mapper.StoreMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final StoreMapper storeMapper;

    public List<ReviewResponseDto> getReviewResponseDtos(final Long storeIdx, final Long reviewIdx) {
        return reviewMapper
                .findReviewsByReviewIdxAndStoreIdx(reviewIdx, storeIdx)
                .stream()
                .map(review -> new ReviewResponseDto(review))
                .collect(Collectors.toList());
    }

    @Transactional
    public Review saveReview(final Long userIdx, final ReviewRequestDto reviewRequestDto) {

        //별점이 0~5인것만 승인
        reviewRequestDto.checkLiked();

        Review review = reviewRequestDto.toEntity(userIdx);

        reviewMapper.saveReview(review);

        Review savedReview = reviewMapper.findReviewByReviewIdx(review.getReviewIdx());

        return savedReview;
    }

    public List<ReviewStoreResponseDto>  getMyReviews(final Long userIdx) {

        List<Store> storeList = storeMapper.findMyReviewOfStoreByUserIdx(userIdx);
        if(storeList == null) return new ArrayList<>();
        List<ReviewStoreResponseDto> reviewStoreResponseDtos = storeList.stream().map(store -> new ReviewStoreResponseDto(store)).collect(Collectors.toList());
        return reviewStoreResponseDtos;
    }

    public List<ReviewStoreResponseDto> getMoreMyReviews(final Long userIdx, final Long reviewIdx) {
        List<Store> storeList = storeMapper.findMoreMyReviewOfStoreByUserIdx(userIdx,reviewIdx);
        if(storeList == null) return new ArrayList<>();
        List<ReviewStoreResponseDto> reviewStoreResponseDtos = storeList.stream().map(store -> new ReviewStoreResponseDto(store)).collect(Collectors.toList());
        return reviewStoreResponseDtos;
    }

    @Transactional
    public void deleteReview(final Long userIdx, final Long reviewIdx) {
        reviewMapper.deleteReviewByUserIdxAndReviewIdx(userIdx, reviewIdx);
    }
}
