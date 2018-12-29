package com.travely.travely.service;

import com.travely.travely.dto.review.ReviewResponseDto;
import com.travely.travely.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
}
