package com.travely.travely.service;

import com.travely.travely.domain.Review;
import com.travely.travely.dto.review.ReviewRequestDto;
import com.travely.travely.dto.review.ReviewResponseDto;
import com.travely.travely.dto.review.ReviewStoreResponseDto;
import com.travely.travely.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    public List<ReviewResponseDto> getReviewResponseDtos(final Long storeIdx) {
        return reviewMapper
                .findReviewsByStoreIdx(storeIdx)
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

        return review;
    }

    public List<ReviewStoreResponseDto> getAllMyReviews(final Long userIdx, final Long offset) {
        //5*1-5,5*1
        //5*offset-5, 5*offset
        final Long start = 5 * offset - 5;
        final Long end = 5 * offset;
        List<ReviewStoreResponseDto> reviewStoreResponseDtos = reviewMapper.findReviewsAndStoreByUserIdx(userIdx, start, end);
        if (reviewStoreResponseDtos == null) return new ArrayList<>();
        return reviewStoreResponseDtos;
    }

    @Transactional
    public void deleteReview(final Long userIdx, final Long reviewIdx) {
        reviewMapper.deleteReviewByUserIdxAndReviewIdx(userIdx, reviewIdx);
    }
}
