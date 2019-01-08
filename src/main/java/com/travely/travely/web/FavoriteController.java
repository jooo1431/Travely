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

    @ApiOperation(value = "즐겨찾기 리스트 조회", notes = "\"regionIdx\": 지역의 고유 번호 Long,\n" +
            "    \"regionName\": \"지역이름 String (ex.홍대입구역,건대입구역...)\",\n" +
            "    \"simpleStoreResponseDtos\": [\n" +
            "      {\n" +
            "        \"storeIdx\": 가게고유번호 Long,\n" +
            "        \"storeName\": \"가게이름\" String\n" +
            "      }\n" +
            "    ]")
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
