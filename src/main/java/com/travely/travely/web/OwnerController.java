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
    @PostMapping("/read/qr")
    ResponseEntity<ReserveInfoDto> getReserveInfoByReserveCode(@RequestBody final String reserveCode){
        //예약코드에 해당하는 예약이 있는지 검색
        if(ownerService.isReserveByReserveCode(reserveCode)){
            ReserveInfoDto reserveInfoDto = ownerService.readQRCode(reserveCode);
            return ResponseEntity.ok().body(reserveInfoDto);
        }else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @ApiOperation(value = "짐 사진 저장", notes = "짐 사진 저장")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "보관시작"),
            @ApiResponse(code = 400, message = "잘못 된 접근"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/reserve/bagImgs")
    ResponseEntity<String> saveBaggagesImgs(@RequestBody final String reserveCode, @ApiIgnore Authentication authentication, @RequestPart(value = "bagImg")final MultipartFile[] bagImgs){
        //업주만 저장할수 있게 한번거른다.
        final long ownerIdx = Long.parseLong((String) authentication.getPrincipal());
        if(ownerService.areYouOwner(reserveCode, ownerIdx)){
            if(ownerService.saveBaggagesPhotos(reserveCode,bagImgs)){
                return ResponseEntity.status(HttpStatus.CREATED).body("saved");
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("failed");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("auth error");
        }
    }


    @ApiOperation(value = "보관 시작", notes = "보관상태 및 결제 완료 상태가 된다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "보관시작"),
            @ApiResponse(code = 400, message = "잘못 된 접근"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/reserve/confirm")
    public ResponseEntity<String> readQrCode(@RequestBody final String reserveCode, @ApiIgnore Authentication authentication) {

        final long ownerIdx = Long.parseLong((String) authentication.getPrincipal());
        final String msg;
        //사장님의 토큰을 받아서 store의 owner와 비교 후 아니면 리젝
        if (ownerService.areYouOwner(reserveCode, ownerIdx)) {
            //예약정보 정상적인지 체크

            //정상적이면
            msg = ownerService.changeReserveStateAndProgressUsingQR(reserveCode);

            return ResponseEntity.ok().body(msg);
        } else{
            msg="NO DATA";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }
}
