package com.travely.travely.web;

import com.travely.travely.dto.store.StoreDetailsResonseDto;
import com.travely.travely.dto.store.StoreListResponseDto;
import com.travely.travely.service.StoreService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @ApiOperation(value = "지역의 상가 목록 조회", notes = "상가명의 목록을 반환합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "세부정보 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("list/{regionIdx}")
    public ResponseEntity<List<StoreListResponseDto>> getStoreList(@PathVariable final long regionIdx) {
        return ResponseEntity.ok().body(storeService.getStoreList(regionIdx));

    }

    @ApiOperation(value = "상가 세부정보 조회", notes = "상가테이블과 지역테이블을 조인한 모든 컬럼을 반환합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "세부정보 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{storeIdx}")
    public ResponseEntity<StoreDetailsResonseDto> getStoreDetails(@PathVariable Long storeIdx) {
        return ResponseEntity.ok(storeService.getStoreDetails(storeIdx));
    }

}
