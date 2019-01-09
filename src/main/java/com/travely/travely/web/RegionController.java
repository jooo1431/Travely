package com.travely.travely.web;

import com.travely.travely.dto.region.RegionResponseDto;
import com.travely.travely.service.RegionService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@Slf4j
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
    public ResponseEntity<List<RegionResponseDto>> getStoreCount(@ApiIgnore Authentication authentication) {

        return ResponseEntity.ok().body(regionService.getAllRegion());
    }
}
