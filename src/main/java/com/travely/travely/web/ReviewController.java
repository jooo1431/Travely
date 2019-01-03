package com.travely.travely.web;

import com.travely.travely.domain.Review;
import com.travely.travely.dto.review.ReviewDto;
import com.travely.travely.service.ReviewService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @ApiOperation(value = "모든 리뷰 조회", notes = "모든 리뷰를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "리뷰 조회 성공"),
            @ApiResponse(code = 500, message = "서버 에러")})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("")
    public ResponseEntity getAllReview(@ApiIgnore Authentication authentication){
        Long userIdx = Long.parseLong((String)authentication.getPrincipal());
        List<ReviewDto> reviewDtos = reviewService.findAll(userIdx);
        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }


    @ApiOperation(value = "리뷰 조회", notes = "리뷰를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "리뷰 조회 성공"),
            @ApiResponse(code = 500, message = "서버 에러")})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{reviewIdx}")
    public ResponseEntity getReview(@ApiIgnore Authentication authentication,
//                               @RequestParam(value="reviewIdx") final Long reviewIdx){
                                    @PathVariable() final Long reviewIdx){
        Long userIdx = Long.parseLong((String)authentication.getPrincipal());
        ReviewDto reviewDto = reviewService.findByReviewIdx(userIdx, reviewIdx);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }


    @ApiOperation(value = "리뷰 작성", notes = "리뷰를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "리뷰 작성 성공"),
            @ApiResponse(code = 500, message = "서버 에러")})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("")
    public ResponseEntity saveReview(@ApiIgnore Authentication authentication,
                                     @RequestBody final ReviewDto reviewDto) {

        Long userIdx = Long.parseLong((String)authentication.getPrincipal());

        reviewService.saveReview(userIdx, reviewDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "리뷰 수정", notes = "리뷰를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "리뷰 수정 성공"),
            @ApiResponse(code = 500, message = "서버 에러")})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/{reviewIdx}")
    public ResponseEntity updateReview(@ApiIgnore Authentication authentication,
                                       @PathVariable() final Long reviewIdx,
                                       @RequestBody final ReviewDto reviewDto){
        Long userIdx = Long.parseLong((String)authentication.getPrincipal());
        reviewDto.setReviewIdx(reviewIdx);
        reviewService.updateReview(userIdx, reviewDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "리뷰 삭제", notes = "리뷰를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "리뷰 삭제 성공"),
            @ApiResponse(code = 500, message = "서버 에러")})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/{reviewIdx}")
    public ResponseEntity delectReview(@ApiIgnore Authentication authentication,
                            @PathVariable() final Long reviewIdx){
        Long userIdx = Long.parseLong((String)authentication.getPrincipal());
        reviewService.deleteReview(userIdx, reviewIdx);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}