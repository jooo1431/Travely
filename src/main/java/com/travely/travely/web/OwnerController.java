package com.travely.travely.web;

import com.travely.travely.dto.owner.AllReserveResponseDto;
import com.travely.travely.dto.owner.ReserveArchiveInfoResponseDto;
import com.travely.travely.dto.owner.ReserveArchiveResponseDto;
import com.travely.travely.dto.review.ReviewUserImgResponseDto;
import com.travely.travely.service.OwnerService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @ApiOperation(value = "보관 내역 상세 조회", notes = "  \"reserveIdx\": 예약 고유번호입니다.Long\n" +
            "  \"userIdx\": 사용자 고유번호입니다. Long\n" +
            "  \"userImgUrl\": \"사용자 프로필 경로 입니다. String\",\n" +
            "  \"userName\": \"사용자의 이름입니다. String\",\n" +
            "  \"userPhone\": \"000-0000-0000 형식의 사용자 전화번호 입니다. String\",\n" +
            "  \"payType\": \"CASH,CARD 와 같은 Enum타입의 결제방식입니다. String\",\n" +
            "  \"bagDtoList\": [\n" +
            "    {\n" +
            "      \"bagType\": \"CARRIER,ETC 로 Enum타입의 짐의 종류를 나타냅니다. String\",\n" +
            "      \"bagCount\": 가방 갯수입니다. Long\n" +
            "    }\n" +
            "  ],\n" +
            "  \"startTime\": getTime한 밀리세컨드 값입니다. Long 이하 시간값은 전부 동일합니다.\n" +
            "  \"depositTime\": \n" +
            "  \"endTime\": \n" +
            "  \"takeTime\": \n" +
            "  \"progressType\": \"ING,DONE,CANCEL 결제 상태를 나타내는 Enum 타입입니다. String\",\n" +
            "  \"price\": 총 가격을 나타냅니다. Long\n" +
            "  \"bagImgDtos\": [bagImgUrl :가방의 이미지 주소가 들어가는 배열입니다.] String\n" +
            "  \"overTime\": 짐을 찾아가야 하는시간이 현재시간보다 초과했을 경우 0보다 큰 getTime값이 출력됩니다. 아닐경우에는 0이 출력됩니다. Long")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "보관내역 상세 조회 성공"),
            @ApiResponse(code = 401, message = "인증 에러"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/reserve/{reserveIdx}")
    public ResponseEntity<ReserveArchiveInfoResponseDto> getArchiveByReserveIdx(@ApiIgnore Authentication authentication, @PathVariable final Long reserveIdx) {

        Long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        ReserveArchiveInfoResponseDto reserveArchiveInfoResponseDto = ownerService.getArchiveByReserveIdx(ownerIdx, reserveIdx);

        return ResponseEntity.ok(reserveArchiveInfoResponseDto);
    }

    @ApiOperation(value = "짐 보관 및 픽업", notes = "짐 보관 및 픽업시 reserve의 state타입 변경")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "짐 보관 및 픽업 성공"),
            @ApiResponse(code = 401, message = "인증 에러"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/reserve/{reserveIdx}")
    public ResponseEntity<Void> updateReserveToPickUp(@ApiIgnore Authentication authentication, @PathVariable final Long reserveIdx) {

        Long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        ownerService.updateBaggageState(ownerIdx, reserveIdx);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "가게 리뷰 보기", notes = "가게 리뷰 보기")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "가게 리뷰 조회 성공"),
            @ApiResponse(code = 401, message = "인증 에러"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/review")
    public ResponseEntity<List<ReviewUserImgResponseDto>> getRiviews(@ApiIgnore Authentication authentication) {

        Long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        List<ReviewUserImgResponseDto> reviewUserImgResponseDtos = ownerService.getReviews(ownerIdx);

        return ResponseEntity.ok().body(reviewUserImgResponseDtos);
    }

    @ApiOperation(value = "가게 리뷰 추가 보기", notes = "가게 리뷰 추가 보기")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "가게 리뷰 추가 조회 성공"),
            @ApiResponse(code = 401, message = "인증 에러"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/review/{reviewIdx}")
    public ResponseEntity<List<ReviewUserImgResponseDto>> getMoreRiviews(@ApiIgnore Authentication authentication, @PathVariable Long reviewIdx) {

        Long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        List<ReviewUserImgResponseDto> reviewUserImgResponseDtos = ownerService.getMoreReviews(ownerIdx, reviewIdx);

        return ResponseEntity.ok(reviewUserImgResponseDtos);
    }

    @ApiOperation(value = "가게 예약 보관 목록 조회", notes = "가게 예약 보관 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "가게 예약 보관 목록 조회"),
            @ApiResponse(code = 401, message = "인증 에러"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/reserve")
    public ResponseEntity<AllReserveResponseDto> getAllReserveArchive(@ApiIgnore Authentication authentication) {
        Long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        List<ReserveArchiveResponseDto> reserveResponseDtos = ownerService.getReserved(ownerIdx);
        List<ReserveArchiveResponseDto> storeResponseDtos = ownerService.getStoring(ownerIdx);

        AllReserveResponseDto allReserveResponseDto = ownerService.getReservedAndStoring(reserveResponseDtos, storeResponseDtos);

        return ResponseEntity.ok().body(allReserveResponseDto);
    }

    @ApiOperation(value = "예약코드 조회", notes = "예약코드 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "예약코드 조회 성공"),
            @ApiResponse(code = 400, message = "예약 및 보관내역 없음"),
            @ApiResponse(code = 401, message = "인증 에러"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/reserve/{storeIdx}/{reserveCode}")
    public ResponseEntity<ReserveArchiveInfoResponseDto> readReserveCode(@ApiIgnore Authentication authentication, @PathVariable final Long storeIdx, @PathVariable final String reserveCode) {

        Long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        ReserveArchiveInfoResponseDto reserveArchiveInfoResponseDto = ownerService.readReserveCode(ownerIdx, storeIdx, reserveCode);

        return ResponseEntity.ok(reserveArchiveInfoResponseDto);
    }
}
