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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Slf4j
@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;



    @ApiOperation(value = "예약상태 저장", notes = "예약상태 저장 후 예약 정보 반환")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "예약 성공"),
            @ApiResponse(code = 400, message = "잘못 된 접근"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/save")
    public ResponseEntity<ReservationResponse> saveReservation(@ApiIgnore Authentication authentication, @RequestBody final ReservationRequest reservationRequest) {

        long userIdx = Long.parseLong((String)authentication.getPrincipal());

        ReservationResponse reservationResponse = reservationService.saveReservation(userIdx,reservationRequest);

        if(reservationResponse==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationResponse);
    }


}
