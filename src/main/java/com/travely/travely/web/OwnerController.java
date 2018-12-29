package com.travely.travely.web;

import com.travely.travely.dto.reservation.ReserveInfoDto;
import com.travely.travely.service.OwnerService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @ApiOperation(value = "예약정보 상세확인", notes = "QR 코드 리드 혹은 reserveCode로 예약자의 예약내역 확인")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "확인성공"),
            @ApiResponse(code = 400, message = "잘못 된 접근"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/read")
    ResponseEntity<ReserveInfoDto> getReserveInfoByReserveCode(final String reserveCode) {
        //예약코드에 해당하는 예약이 있는지 검색
        if (ownerService.isReserveByReserveCode(reserveCode)) {
            ReserveInfoDto reserveInfoDto = ownerService.readReserveCode(reserveCode);
            return ResponseEntity.ok().body(reserveInfoDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


    @ApiOperation(value = "보관 시작 및 사진저장", notes = "짐 사진이 저장되고 보관상태 및 결제 완료 상태가 된다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "보관시작"),
            @ApiResponse(code = 400, message = "잘못 된 접근"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/update/confirm")
    public ResponseEntity<Void> readQrCode(final String reserveCode, @ApiIgnore Authentication authentication, @RequestPart(value = "bagImg") final MultipartFile[] bagImgs) {

        final long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        //사장님의 토큰을 받아서 store의 owner와 비교 후 아니면 리젝
        if (ownerService.areYouOwner(reserveCode, ownerIdx)) {
            //예약정보 정상적인지 체크

            //정상적이면
            ownerService.changeStateSavePhoto(reserveCode, bagImgs);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation(value = "보관 중인 짐 내역 보기", notes = "업주의 ownerIdx를 이용하여 보관중임 짐 내역을 본다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 400, message = "잘못 된 접근"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/read/list")
    public ResponseEntity<Void> readList(@ApiIgnore Authentication authentication){

        final long ownerIdx = Long.parseLong((String) authentication.getPrincipal());



        return null;
    }

}
