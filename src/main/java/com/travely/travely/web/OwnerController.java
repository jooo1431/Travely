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

    @ApiOperation(value = "보관 내역 상세 조회", notes = "보관 내역 상세 조회")
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
            @ApiResponse(code = 204, message = "리뷰 없음"),
            @ApiResponse(code = 401, message = "인증 에러"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/review")
    public ResponseEntity<List<ReviewUserImgResponseDto>> getRiviews(@ApiIgnore Authentication authentication) {

        Long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        List<ReviewUserImgResponseDto> reviewUserImgResponseDtos = ownerService.getReviews(ownerIdx);

        return ResponseEntity.ok(reviewUserImgResponseDtos);
    }

    @ApiOperation(value = "가게 리뷰 추가 보기", notes = "가게 리뷰 추가 보기")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "가게 리뷰 추가 조회 성공"),
            @ApiResponse(code = 204, message = "리뷰 없음"),
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
            @ApiResponse(code = 204, message = "예약 및 보관내역 없음"),
            @ApiResponse(code = 401, message = "인증 에러"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/reserve")
    public ResponseEntity<AllReserveResponseDto> getAllReserveArchive(@ApiIgnore Authentication authentication) {

        Long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        List<ReserveArchiveResponseDto> reserveResponseDtos = ownerService.getReserved(ownerIdx);
        List<ReserveArchiveResponseDto> storeResponseDtos = ownerService.getStoring(ownerIdx);

        AllReserveResponseDto allReserveResponseDto = ownerService.getReservedAndStoring(reserveResponseDtos, storeResponseDtos);

        return ResponseEntity.ok(allReserveResponseDto);
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
