package com.travely.travely.web;

import com.travely.travely.dto.favorite.FavoriteStoreDto;
import com.travely.travely.service.FavoriteService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @ApiOperation(value = "즐겨찾는 상가 목록 조회", notes = "지역별 즐겨찾는 상가를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "즐겨찾는 상가 정보 반환 성공"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/api/favorite")
    public ResponseEntity getFavoriteStoreList(@ApiIgnore Authentication authentication,
                                               @RequestParam("regionIdx") final Long regionIdx) {
        Long userIdx = Long.parseLong((String)authentication.getPrincipal());

        List<FavoriteStoreDto> favoriteStoreDtos = favoriteService.getFavoriteStore(userIdx, regionIdx);
        return new ResponseEntity<>(favoriteStoreDtos, HttpStatus.OK);
    }
}