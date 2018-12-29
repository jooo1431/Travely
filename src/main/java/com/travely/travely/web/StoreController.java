package com.travely.travely.web;

import com.travely.travely.dto.store.StoreDetailsResponseDto;
import com.travely.travely.dto.store.StoreListResponseDto;
import com.travely.travely.service.ReviewService;
import com.travely.travely.service.StoreService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    private final ReviewService reviewService;

    @ApiOperation(value="지역의 상가 목록 조회",notes="상가명의 목록을 반환합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "세부정보 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("list")
    public ResponseEntity<List<StoreListResponseDto>> getStoreList(@RequestParam("regionIdx") final long regionIdx){
        List<StoreListResponseDto> storeListResponseDtos = storeService.getStoreList(regionIdx);
        if(storeListResponseDtos != null) return ResponseEntity.ok().body(storeListResponseDtos);
        else return ResponseEntity.ok().build();
    }

    @ApiOperation(value="상가 세부정보 조회",notes="상가테이블과 지역테이블을 조인한 모든 컬럼을 반환합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "세부정보 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{storeIdx}")
    public ResponseEntity<StoreDetailsResponseDto> getStoreDetails(@PathVariable Long storeIdx){
        return ResponseEntity.ok(new StoreDetailsResponseDto(storeService.getStoreDetails(storeIdx),
                reviewService.getReviewResponseDtos(storeIdx)));
    }
}
