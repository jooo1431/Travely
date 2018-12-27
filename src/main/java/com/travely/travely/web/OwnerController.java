package com.travely.travely.web;

import com.travely.travely.service.OwnerService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @ApiOperation(value = "QR코드 리드", notes = "QR코드 리드후 상태변경")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "보관시작"),
            @ApiResponse(code = 400, message = "잘못 된 접근"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/qr/read")
    public ResponseEntity<Void> readQrCode(@RequestBody final String reserveCode, @ApiIgnore Authentication authentication) {

        final long ownerIdx = (long) authentication.getPrincipal();

        //사장님의 토큰을 받아서 store의 owner와 비교 후 아니면 리젝
        if (ownerService.areYouOwner(reserveCode, ownerIdx)) {
            //예약정보 정상적인지 체크

            //정상적이면
            ownerService.changeReserveStateAndProgressUsingQR(reserveCode);


            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
