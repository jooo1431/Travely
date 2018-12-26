package com.travely.travely.web;

import com.travely.travely.dto.store.StoreDetailsResponseDto;
import com.travely.travely.dto.store.StoreListResponseDto;
import com.travely.travely.service.StoreService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @ApiOperation(value = "제휴상가 지역별 목록 조회", notes = "지역별 이름과 상가수를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "목록조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("list")
    public ResponseEntity<List<StoreListResponseDto>> getStoreList() {
        List<StoreListResponseDto> storeListResponseDtos = storeService.getEntireStoreList();
        if (storeListResponseDtos != null) return ResponseEntity.ok().body(storeListResponseDtos);
        else return ResponseEntity.ok().build();
    }



    @ApiOperation(value="지역별 상가 세부정보 조회",notes="상가명, 전화번호, 홈페이지, 영업시간, 주소를 반환합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "세부정보 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("details")
    public ResponseEntity<List<StoreDetailsResponseDto>> getStoreDetails(){
        List<StoreDetailsResponseDto> storeDetailsResponseDtos = storeService.getStoreInfoDetails();
        if(storeDetailsResponseDtos != null) return ResponseEntity.ok().body(storeDetailsResponseDtos);
        else return ResponseEntity.ok().build();

    }
}
