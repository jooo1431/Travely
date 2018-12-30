package com.travely.travely.service;

import com.travely.travely.domain.Review;
import com.travely.travely.dto.review.ReviewDto;
import com.travely.travely.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    /**
     * 전체리뷰 조회
     * @return reviewList
     */
    public List<ReviewDto> findAll(final Long userIdx) {
        List<ReviewDto> reviews = reviewMapper.findAll(userIdx);
        return reviews;
    }

    /**
     * reviewIdx를 사용한 리뷰 조회
     * @param userIdx
     * @param reviewIdx
     * @return reviewDto
     */
    public ReviewDto findByReviewIdx(final Long userIdx, final Long reviewIdx) {
        ReviewDto reviewDto = reviewMapper.findByReviewIdx(userIdx, reviewIdx);
        return reviewDto;
    }


    /**
     * review 저장
     * @param userIdx
     * @param reviewDto
     * @return HttpStatus
     */
    @Transactional
    public HttpStatus saveReview(final Long userIdx, final ReviewDto reviewDto) {
        if( reviewDto.checkProperties() ) {
            Review review = reviewDto.toEntity();
            reviewMapper.saveReview(userIdx, review);
            return HttpStatus.OK;
        }
        else return  HttpStatus.BAD_REQUEST;
    }

    /**
     * review 수정
     * @param userIdx
     * @param reviewDto
     * @return HttpStatus
     */
    @Transactional
    public HttpStatus updateReview(final Long userIdx, final ReviewDto reviewDto) {
        if( reviewDto.checkProperties() ) {
            Review review = reviewDto.toEntity();
            reviewMapper.getUpdate(userIdx, review);
            return HttpStatus.OK;
        }
        else return HttpStatus.BAD_REQUEST;
    }

    /**
     * 리뷰 삭제
     * @param userIdx
     * @param reviewIdx
     * @return HttpStatus
     */
    @Transactional
    public HttpStatus deleteReview(final Long userIdx, final Long reviewIdx){
        reviewMapper.deleteByReviewIdx(userIdx, reviewIdx);
        return HttpStatus.OK;
    }
}