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

    @ApiOperation(value = "상가 세부정보 조회", notes = "상가테이블과 지역테이블을 조인한 모든 컬럼을 반환합니다\n" +
            "Long storeIdx;\n" +
            "Long ownerIdx;\n" +
            "String storeName;\n" +
            "String storeCall;\n" +
            "String storeUrl;\n" +
            "String address;\n" +
            "Long openTime;\n" +
            "Long closeTime;\n" +
            "Double latitude;\n" +
            "Double longitude;\n" +
            "Long limit;-> 상가 최대 수용량\n" +
            "Long currentBag;-> 현재 상가 수용중인 짐의 양\n" +
            "Double grade;-> 상가 평점\n" +
            "String addressNumber;\n" +
            "int available; -> -1 == close, 1 == open")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "세부정보 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{storeIdx}")
    public ResponseEntity<StoreDetailsResonseDto> getStoreDetails(@PathVariable Long storeIdx, @ApiIgnore Authentication authentication) {
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(storeService.getStoreDetails(storeIdx,userIdx));
    }

    @ApiOperation(value = "상가에 대한 리뷰 조회", notes = "상가의 리뷰를 조회합니다. 상가인덱스와 최근 리뷰의 마지막 인데스를 불러 조회합니다.")
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
