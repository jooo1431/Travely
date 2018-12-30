package com.travely.travely.web;

import com.travely.travely.dto.store.RegionResponseDto;
import com.travely.travely.dto.store.StoreListResponseDto;
import com.travely.travely.mapper.RegionMapper;
import com.travely.travely.service.RegionService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/region")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @ApiOperation(value = "제휴상가 지역별 목록 조회", notes = "지역명과 상가수를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "목록조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public ResponseEntity<List<RegionResponseDto>> getStoreCount() {
        return ResponseEntity.ok().body(regionService.getStoreCount());
    }

    @ApiOperation(value = "지역의 상가 목록 조회", notes = "상가명의 목록을 반환합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "세부정보 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{regionIdx}/store")
    public ResponseEntity<List<StoreListResponseDto>> getStoreList(@PathVariable final long regionIdx) {
        return ResponseEntity.ok().body(regionService.getStoreList(regionIdx));
    }

}
