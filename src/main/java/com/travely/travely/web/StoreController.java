package com.travely.travely.web;

import com.travely.travely.dto.store.StoreInfoResponseDto;
import com.travely.travely.service.StoreService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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
    @PostMapping
    public ResponseEntity<List<StoreInfoResponseDto>> getStore() {
        List<StoreInfoResponseDto> storeInfoResponseDtos = storeService.getStoreInfo();
        if (storeInfoResponseDtos != null) return ResponseEntity.ok().body(storeInfoResponseDtos);
        else return ResponseEntity.ok().build();
    }
}
