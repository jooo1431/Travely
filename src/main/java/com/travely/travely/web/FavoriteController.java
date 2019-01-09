package com.travely.travely.web;

import com.travely.travely.dto.favorite.FavoriteResponseDto;
import com.travely.travely.dto.region.RegionResponseDto;
import com.travely.travely.service.FavoriteService;
import com.travely.travely.service.RegionService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    private final RegionService regionService;

    @ApiOperation(value = "상가를 즐겨찾기 하는 기능", notes = "즐겨찾기 상태여부에 따라 상태를 바꿔주고 아니였다면 목록에 추가해준다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "즐겨찾기 변경 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/{storeIdx}")
    public ResponseEntity<FavoriteResponseDto> addOrDeleteFavorite(@PathVariable Long storeIdx, @ApiIgnore Authentication authentication) {
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(favoriteService.updateFavorite(userIdx, storeIdx));
    }

    @ApiOperation(value = "즐겨찾기 리스트 조회", notes = "  {\n" +
            "    \"regionName\": \"string\",\n" +
            "    \"simpleStoreResponseDtos\": [\n" +
            "      {\n" +
            "        \"address\": \"string\",\n" +
            "        \"closeTime\": 0/Long,\n" +
            "        \"currentBag\": 0/Long,\n" +
            "        \"grade\": 0/double,\n" +
            "        \"limit\": 0/Long,\n" +
            "        \"openTime\": 0/Long,\n" +
            "        int available -> -1, 1로 판별" +
            "        \"restWeekResponseDtos\": [\n" +
            "          {\n" +
            "            \"storeIdx\": 0/Long,\n" +
            "            \"week\": 0/Long,\n" +
            "            \"weekIdx\": 0/Long\n" +
            "          }\n" +
            "        ],\n" +
            "        \"storeIdx\": 0/Long,\n" +
            "        \"storeImgUrl\": \"string\",\n" +
            "        \"storeName\": \"string\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "즐겨찾기 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping
    public ResponseEntity<List<RegionResponseDto>> getAllRegionByFavorite(@ApiIgnore Authentication authentication) {
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(regionService.getAllRegion(userIdx));
    }
}
