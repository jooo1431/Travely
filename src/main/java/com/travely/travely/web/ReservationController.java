package com.travely.travely.web;

import com.travely.travely.dto.reservation.ReserveResponseDto;
import com.travely.travely.dto.reservation.ReserveRequestDto;
import com.travely.travely.service.ReservationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.Date;
import java.sql.Timestamp;

@Slf4j
@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    @ApiOperation(value = "예약상태 저장", notes = "예약상태 저장 후 예약 정보 반환")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "예약 성공"),
            @ApiResponse(code = 400, message = "잘못 된 정보 주입 시도"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/save")
    public ResponseEntity<ReserveResponseDto> saveReservation(@ApiIgnore Authentication authentication, @RequestBody final ReserveRequestDto reserveRequestDto) {

        log.info("time" + new Timestamp(reserveRequestDto.getStartTime()));
        log.info("time" + new Date(reserveRequestDto.getStartTime()));

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
    @DeleteMapping("/cancel")
    public ResponseEntity<Void> cancelReservation(@ApiIgnore Authentication authentication) {

        Long userIdx = Long.parseLong((String) authentication.getPrincipal());

        reservationService.cancelReservation(userIdx);

        return ResponseEntity.ok().build();
    }
}


