package com.travely.travely.web;

import com.travely.travely.dto.reservation.PriceResponseDto;
import com.travely.travely.dto.reservation.ReserveRequestDto;
import com.travely.travely.dto.reservation.ReserveResponseDto;
import com.travely.travely.dto.reservation.ReserveViewResponseDto;
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
    @PostMapping("")
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
    @DeleteMapping("")
    public ResponseEntity<Void> cancelReservation(@ApiIgnore Authentication authentication) {
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());

        reservationService.cancelReservation(userIdx);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "예약 세부정보 조회", notes = "userIdx 예약 내용 조회\n" +
            "stateType : 예약의 상태를 나타내는 Enum입니다 RESERVED,PAYED,ARCHIVE,PICKUP,CANCEL. String으로 읽으세요.\n" +
            "reserveCode : 예약고유코드로 String입니다.\n" +
            "startTime, endTime : getTime한 밀리세컨드 값으로 Long입니다.\n" +
            "depositTime, takeTime 도 동일합니다.\n" +
            "bagDtos : 짐 정보가 포함된 json 배열입니다.\n" +
            "내부에 bagType Enum으로 CARRIER, ETC입니다 String으로 읽으세요. \n" +
            "bagCount : Long이 있습니다.\n" +
            "priceIdx는 가격표에 표시되는 시간값입니다.\n" +
            "priceUnit은 가격표에 해당하는 가격입니다.\n" +
            "extraChargeCount는 가격표에서 12시간 마다 책정되는 4000원의 횟수를 나타냅니다.\n" +
            "extraCharge는 추가요금에 해당하는 4000원입니다.\n" +
            "price는 최종 가격입니다.\n" +
            "payType Enum타입으로 결제 수단을 나타냅니다. CASH, CARD가 있습니다. String으로 읽으세요.\n" +
            "progressType Enum타입으로 결제 진행상태를 나타냅니다. ING, DONE, CANCEL이 있습니다. String으로 읽으세요.\n" +
            "baImgDtos는 bagImgUrl을 담고 있는 리스트입니다.\n" +
            "store는 가게의 정보를 담고있습니다. 세부정보는 다음과 같습니다.\n" +
            "ownerName 업주이름 String \n" +
            "storeName 상가이름 String \n" +
            "storeCall 상가전화번호 String\n" +
            "latitude 상가위도 double\n" +
            "longitude 상가경도 double\n" +
            "openTime 상가영업시작시간 Long\n" +
            "closeTime 상가영업종료시간 Long\n" +
            "address 상가주소 String\n" +
            "addressNumber 상가지번주소 addressNumber\n" +
            "storeIdx 상가Id값 Long")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "예약 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("")
    public ResponseEntity<ReserveViewResponseDto> getReservation(@ApiIgnore Authentication authentication) {

        Long userIdx = Long.parseLong((String) authentication.getPrincipal());

        ReserveViewResponseDto reserveViewResponseDto = reservationService.getReserveMyInfo(userIdx);

        return ResponseEntity.ok().body(reserveViewResponseDto);
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

    @ApiOperation(value = "예약 세부정보 조회", notes = "reserveIdx 예약 내용 조회\n" +
            "stateType : 예약의 상태를 나타내는 Enum입니다 RESERVED,PAYED,ARCHIVE,PICKUP,CANCEL. String으로 읽으세요.\n" +
            "reserveCode : 예약고유코드로 String입니다.\n" +
            "startTime, endTime : getTime한 밀리세컨드 값으로 Long입니다.\n" +
            "depositTime, takeTime 도 동일합니다.\n" +
            "bagDtos : 짐 정보가 포함된 json 배열입니다.\n" +
            "내부에 bagType Enum으로 CARRIER, ETC입니다 String으로 읽으세요. \n" +
            "bagCount : Long이 있습니다.\n" +
            "priceIdx는 가격표에 표시되는 시간값입니다.\n" +
            "priceUnit은 가격표에 해당하는 가격입니다.\n" +
            "extraChargeCount는 가격표에서 12시간 마다 책정되는 4000원의 횟수를 나타냅니다.\n" +
            "extraCharge는 추가요금에 해당하는 4000원입니다.\n" +
            "price는 최종 가격입니다.\n" +
            "payType Enum타입으로 결제 수단을 나타냅니다. CASH, CARD가 있습니다. String으로 읽으세요.\n" +
            "progressType Enum타입으로 결제 진행상태를 나타냅니다. ING, DONE, CANCEL이 있습니다. String으로 읽으세요.\n" +
            "baImgDtos는 bagImgUrl을 담고 있는 리스트입니다.\n" +
            "store는 가게의 정보를 담고있습니다. 세부정보는 다음과 같습니다.\n" +
            "ownerName 업주이름 String \n" +
            "storeName 상가이름 String \n" +
            "storeCall 상가전화번호 String\n" +
            "latitude 상가위도 double\n" +
            "longitude 상가경도 double\n" +
            "openTime 상가영업시작시간 Long\n" +
            "closeTime 상가영업종료시간 Long\n" +
            "address 상가주소 String\n" +
            "addressNumber 상가지번주소 addressNumber\n" +
            "storeIdx 상가Id값 Long")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "예약 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{reserveIdx}")
    public ResponseEntity<ReserveViewResponseDto> getReservationByUserIdxAndReserveIdx(@ApiIgnore Authentication authentication,@PathVariable final Long reserveIdx) {

        Long userIdx = Long.parseLong((String) authentication.getPrincipal());

        ReserveViewResponseDto reserveViewResponseDto = reservationService.getReserveMyInfoByReserveIdx(userIdx,reserveIdx);

        return ResponseEntity.ok().body(reserveViewResponseDto);
    }
}


