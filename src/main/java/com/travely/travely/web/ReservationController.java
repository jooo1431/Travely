package com.travely.travely.web;

import com.travely.travely.dto.reservation.ReservationRequest;
import com.travely.travely.dto.reservation.ReservationQR;
import com.travely.travely.service.ReservationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    public ResponseEntity<ReservationQR> saveReservation(@ApiIgnore Authentication authentication, @RequestBody final ReservationRequest reservationRequest) {

        Long userIdx = (Long) authentication.getPrincipal();

        //짐수용한도 체크하기
        final long limit = reservationService.checkLimit(reservationRequest.getStoreIdx());

        if (limit <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        //맡기고자 하는 짐의 양이 limit보다 많으면
        if (!(reservationService.isFull(reservationRequest.getBagDtos(), limit)))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        ReservationQR reservationQR = reservationService.saveReservation(userIdx, reservationRequest);
        //예약작업
        if (reservationQR == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationQR);
    }

    @ApiOperation(value = "예약 취소", notes = "예약상태 조회 후 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "[예약 취소 성공] 혹은 [예약 내역 없음] 출력"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelReservation(@ApiIgnore Authentication authentication) {

        long userIdx = Long.parseLong((String) authentication.getPrincipal());
        String msg = reservationService.cancelReservation(userIdx);

        //비밀번호 체크하는부분추가해야함

        if (msg == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.ok().body(msg);
    }

    @ApiOperation(value = "예약 조회", notes = "예약상태 조회 후 결과 반환")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "예약 조회 성공"),
            @ApiResponse(code = 204, message = "예약 내역 없음"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/info")
    public ResponseEntity<ReservationQR> getReservation(@ApiIgnore Authentication authentication) {

        long userIdx = Long.parseLong((String) authentication.getPrincipal());

        ReservationQR reservationQR = reservationService.getReservation(userIdx);

        if (reservationQR == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok().body(reservationQR);
    }

}
