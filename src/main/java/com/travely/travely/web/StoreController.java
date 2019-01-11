package com.travely.travely.web;

import com.travely.travely.dto.review.ReviewResponseDto;
import com.travely.travely.dto.store.StoreDetailsResonseDto;
import com.travely.travely.service.ReviewService;
import com.travely.travely.service.StoreService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    private final ReviewService reviewService;

    @ApiOperation(value = "상가 세부정보 조회", notes = "" +
            "Long storeIdx;상가고유번호\n" +
            "Long ownerIdx;업주고유번호\n" +
            "String storeName;상가이름\n" +
            "String storeCall;상가전화번호\n" +
            "String storeUrl;상가홈페이지주소\n" +
            "String address;상가도로명주소\n" +
            "Long openTime;영업시작시간\n" +
            "Long closeTime;영업종료시간\n" +
            "Double latitude;위도\n" +
            "Double longitude;경도\n" +
            "Long limit;최대수용량\n" +
            "Long currentBag;현재수용량\n" +
            "Double grade;가게평점\n" +
            "String addressNumber;가게지번주소\n" +
            "int available;영업여부\n" +
            "int isFavorite;즐겨찾기여부\n" +
            "List<ReviewResponseDto> reviewResponseDtos;리뷰목록\n" +
            "List<StoreImageResponseDto> storeImageResponseDto;상가사진\n" +
            "List<RestWeekResponseDto> restWeekResponseDtos;휴일목록")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "세부정보 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{storeIdx}")
    public ResponseEntity<StoreDetailsResonseDto> getStoreDetails(@PathVariable Long storeIdx, @ApiIgnore Authentication authentication) {
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(storeService.getStoreDetails(storeIdx, userIdx));
    }

    @ApiOperation(value = "상가에 대한 리뷰 조회", notes = "상가의 리뷰를 조회합니다. 상가인덱스와 최근 리뷰의 마지막 인데스를 불러 조회합니다." +
            "Long reviewIdx;리뷰고유번호\n" +
            "Long storeIdx;상가고유번호\n" +
            "Long userIdx;리뷰한사람의 고유번호\n" +
            "String content;리뷰내용\n" +
            "Long like;리뷰점수\n" +
            "Long createdAt;리뷰작성일\n" +
            "String userName;리뷰한 사람의 이름")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "세부정보 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{storeIdx}/reviews/{reviewIdx}")
    public ResponseEntity<List<ReviewResponseDto>> getStoreDetails(@PathVariable Long storeIdx, @PathVariable Long reviewIdx) {
        return ResponseEntity.ok(reviewService.getReviewResponseDtos(storeIdx, reviewIdx));
    }
}
