package com.travely.travely.web;

import com.travely.travely.dto.reservation.ReservationRequest;
import com.travely.travely.dto.reservation.ReservationResponse;
import com.travely.travely.service.ReservationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;



    @ApiOperation(value = "예약상태 저장", notes = "예약상태 저장 후 예약 정보 반환")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "예약 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/save")
    public ResponseEntity<ReservationResponse> saveReservation(@RequestBody final ReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = reservationService.saveReservation(reservationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationResponse);
    }

    @ApiOperation(value = "예약 취소", notes = "예약 취소")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "예약 취소 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteReservation(@Param("userIdx") final String userIdx) {

        if(reservationService.deleteReservation(userIdx)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @ApiOperation(value = "예약 조회", notes = "예약 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "예약 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/info")
    public ResponseEntity<ReservationResponse> getReservationInfo(@Param("userIdx") final String userIdx) {

        ReservationResponse reservationResponse = reservationService.getReservation(userIdx);
        if(reservationResponse==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.ok().body(reservationResponse);

    }

}
