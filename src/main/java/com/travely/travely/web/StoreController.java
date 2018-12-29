package com.travely.travely.web;

import com.travely.travely.dto.store.*;
import com.travely.travely.service.StoreService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

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
    @PostMapping("details")
    public ResponseEntity<StoreDetailsResponseDto> getStoreDetails(@RequestParam("storeIdx") final long storeIdx){

        StoreDetailsResponseDto storeDetailsResponseDto = storeService.getStoreDetails(storeIdx);

        if(storeDetailsResponseDto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.ok().body(storeDetailsResponseDto);

    }

    /*@ApiOperation(value="상가사진 목록 조회",notes="상가 사진이 저장된 주소를 반환합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "세부정보 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("details/Img")
    public ResponseEntity<List<StoreImgDto>> getStoreDetailsImg(@RequestParam("regionIdx") final long regionIdx){
        List<StoreImgDto> storeImgDtos = storeService.getStoreDetailsImg(regionIdx);
        if(storeImgDtos != null) return ResponseEntity.ok().body(storeImgDtos);
        else return ResponseEntity.ok().build();

    }*/
}
