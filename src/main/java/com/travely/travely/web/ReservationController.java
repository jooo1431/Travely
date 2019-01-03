package com.travely.travely.web;

import com.travely.travely.dto.reservation.PriceResponseDto;
import com.travely.travely.dto.reservation.ReserveRequestDto;
import com.travely.travely.dto.reservation.ReserveResponseDto;
import com.travely.travely.dto.reservation.ReserveViewDto;
import com.travely.travely.service.ReservationService;
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
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    @ApiOperation(value = "예약상태 저장", notes = "storeIdx : Long 타입으로 주면 됩니다.\n" +
            "startTime : Long 타입으로 timestamp 나 date 타입의 gettime한 밀리세컨드 값입니다.\n" +
            "endTime : Long 타입으로 startTime과 같습니다.\n" +
            "bagDtos : List 형태로 받습니다.\n" +
            "bagDtos.bagType : String 타입으로 'CARRIER' 혹은 'ETC'로 주면 됩니다.\n" +
            "bagDtos.bagCount : Long 타입으로 1 이상의 값을 받야아합니다.\n" +
            "payType : String 타입으로 'CASH' 혹은 'CARD'만 받을 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "예약 성공"),
            @ApiResponse(code = 400, message = "잘못 된 정보 주입 시도"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/")
    public ResponseEntity<ReserveResponseDto> saveReservation(@ApiIgnore Authentication authentication, @RequestBody final ReserveRequestDto reserveRequestDto) {

        Long userIdx = Long.parseLong((String) authentication.getPrincipal());

        ReserveResponseDto reserveResponseDto = reservationService.saveReservation(userIdx, reserveRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(reserveResponseDto);
    }


    @ApiOperation(value = "예약 취소", notes = "예약상태 조회 후 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "예약 취소"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/")
    public ResponseEntity<Void> cancelReservation(@ApiIgnore Authentication authentication) {
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());

        reservationService.cancelReservation(userIdx);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "예약 세부정보 조회", notes = "예약 코드로 예약 내용 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "예약 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{reserveCode}")
    public ResponseEntity<ReserveViewDto> getReservation(@PathVariable("reserveCode") final String reserveCode) {

        ReserveViewDto reserveViewDto = reservationService.getReserveMyInfo(reserveCode);

        return ResponseEntity.ok().body(reserveViewDto);
    }

    @ApiOperation(value = "가격표 조회", notes = "가격표 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/price/list")
    public ResponseEntity<List<PriceResponseDto>> getAllPrice() {

        return ResponseEntity.ok(reservationService.getPrices());
    }
}


