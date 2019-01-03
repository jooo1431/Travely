package com.travely.travely.web;

import com.travely.travely.domain.Region;
import com.travely.travely.mapper.RegionMapper;
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

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final StoreMapper storeMapper;
    private final ReservationMapper reservationMapper;
    private final RegionMapper regionMapper;

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/test")
    public ResponseEntity<Void> saveReservation() {

//        Long time = 1546311600000L;
//
//        Date date = new Date(time);
//
//        log.info("@"+ new Timestamp(time));
//        LocalDateTime ldt = LocalDateTime.of(date.getYear(),date.getMonth(),date.getDay(),date.getHours(),date.getMinutes(),date.getSeconds());
//        ZonedDateTime utcDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
//        ZonedDateTime seoulDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
//        log.info("@"+ldt);
//
//        Long storeIdx = 1L;
//        Long userIdx = 3L;

        List<Region> regions = regionMapper.findAllRegions(1L);

        log.info(regions.get(0).getRegionName());
        log.info(regions.get(0).getStores().get(0).getStoreName());

        return ResponseEntity.ok().build();
    }
}
