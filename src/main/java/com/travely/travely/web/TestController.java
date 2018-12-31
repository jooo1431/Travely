package com.travely.travely.web;

import com.travely.travely.mapper.ReservationMapper;
import com.travely.travely.mapper.StoreMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final StoreMapper storeMapper;
    private final ReservationMapper reservationMapper;

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/test")
    public ResponseEntity<Void> saveReservation() {

        Long storeIdx = 1L;
        Long userIdx = 3L;

        return ResponseEntity.ok().build();
    }
}
