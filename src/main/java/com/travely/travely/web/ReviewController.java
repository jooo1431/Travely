package com.travely.travely.web;

import com.travely.travely.domain.Review;
import com.travely.travely.dto.review.ReviewRequestDto;
import com.travely.travely.dto.review.ReviewStoreResponseDto;
import com.travely.travely.service.ReviewService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = "리뷰 저장", notes = "리뷰 저장")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "리뷰 저장 성공"),
            @ApiResponse(code = 400, message = "잘못 된 정보 주입 시도"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/save")
    public ResponseEntity<Review> saveReservation(@ApiIgnore final Authentication authentication, @RequestBody final ReviewRequestDto reviewRequestDto) {
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());

        final Review review = reviewService.saveReview(userIdx, reviewRequestDto);

        return ResponseEntity.ok().body(review);
    }

    @ApiOperation(value = "나의 리뷰 조회", notes = "리뷰 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "리뷰 조회 성공"),
            @ApiResponse(code = 204, message = "작성 리뷰 없음"),
            @ApiResponse(code = 400, message = "잘못 된 접근"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/")
    public ResponseEntity<List<ReviewStoreResponseDto>> getMyReview(@ApiIgnore final Authentication authentication) {
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());

        List<ReviewStoreResponseDto> reviewStoreResponseDtos = reviewService.getMyReviews(userIdx);

        return ResponseEntity.ok().body(reviewStoreResponseDtos);
    }

    @ApiOperation(value = "나의 리뷰 조회", notes = "리뷰 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "리뷰 조회 성공"),
            @ApiResponse(code = 204, message = "작성 리뷰 없음"),
            @ApiResponse(code = 400, message = "잘못 된 접근"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{reviewIdx}")
    public ResponseEntity<List<ReviewStoreResponseDto>> getMoreMyReview(@ApiIgnore final Authentication authentication, @PathVariable Long reviewIdx) {
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());

        List<ReviewStoreResponseDto> reviewStoreResponseDtos = reviewService.getMoreMyReviews(userIdx, reviewIdx);

        return ResponseEntity.ok().body(reviewStoreResponseDtos);
    }

    @ApiOperation(value = "리뷰 삭제", notes = "사용자의 리뷰 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "리뷰 삭제 성공"),
            @ApiResponse(code = 400, message = "잘못 된 접근"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/delete/{reviewIdx}")
    public ResponseEntity<Void> deleteReview(@ApiIgnore final Authentication authentication, @PathVariable final Long reviewIdx) {
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());
        reviewService.deleteReview(userIdx, reviewIdx);
        return ResponseEntity.ok().build();
    }
}
