package com.travely.travely.web;

import com.travely.travely.dto.favorite.FavoriteStateResponseDto;
import com.travely.travely.dto.favorite.FavoriteStoreResponseDto;
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
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @ApiOperation(value = "상가를 즐겨찾기 하는 기능", notes = "즐겨찾기 상태여부에 따라 상태를 바꿔주고 아니였다면 목록에 추가해준다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "즐겨찾기 변경 성공"),
            @ApiResponse(code = 201, message = "즐겨찾기 추가 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/{storeIdx}")
    public ResponseEntity<FavoriteStateResponseDto> addOrDeleteFavorite(@PathVariable Long storeIdx, @ApiIgnore Authentication authentication) {
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.ok(favoriteService.updateFavorite(userIdx, storeIdx));
    }

//    @ApiOperation(value = "즐겨찾는 상가 목록 조회", notes = "지역별 즐겨찾는 상가를 반환합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "즐겨찾는 상가 정보 반환 성공"),
//            @ApiResponse(code = 500, message = "서버 에러")
//    })
//    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
//    @GetMapping("")
//    public ResponseEntity getFavoriteStoreList(@ApiIgnore Authentication authentication) {
//        Long userIdx = Long.parseLong((String)authentication.getPrincipal());
//
//        return new ResponseEntity<>(favoriteService.getFavoriteStoreList(userIdx), HttpStatus.OK);
//    }
}

