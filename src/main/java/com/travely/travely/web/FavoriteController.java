package com.travely.travely.web;

import com.travely.travely.service.FavoriteService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    @PutMapping
    public ResponseEntity<Void> addOrDeleteFavorite(@RequestParam("userIdx") final long userIdx,
                                              @RequestParam("storeIdx") final long storeIdx) {
        if(favoriteService.GetStateFavorite(userIdx,storeIdx) == 1)
            return ResponseEntity.status(HttpStatus.OK).build();
        else if(favoriteService.GetStateFavorite(userIdx, storeIdx) == 2)
                return ResponseEntity.status(HttpStatus.CREATED).build();
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
}
