package com.travely.travely.web;

import com.travely.travely.dto.owner.ReserveArchiveInfoResponseDto;
import com.travely.travely.dto.owner.OwnerArchiveResponseDto;
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

    final private OwnerService ownerService;

    @ApiOperation(value = "보관 중인 내역 조회", notes = "보관 중인 내역 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "보관내역 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/")
    public ResponseEntity<List<OwnerArchiveResponseDto>> getArchive(@ApiIgnore Authentication authentication) {

        Long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        List<OwnerArchiveResponseDto> ownerArchiveResponseDtos = ownerService.getArchives(ownerIdx);

        return ResponseEntity.ok(ownerArchiveResponseDtos);
    }

    @ApiOperation(value = "보관 중인 내역 추가 조회", notes = "보관 중인 내역 추가 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "보관내역 추가 조회 성공"),
            @ApiResponse(code = 204, message = "추가 내역 없음"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{reserveIdx}")
    public ResponseEntity<List<OwnerArchiveResponseDto>> getMoreArchive(@ApiIgnore Authentication authentication, @PathVariable final Long reserveIdx) {

        Long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        List<OwnerArchiveResponseDto> ownerArchiveResponseDtos = ownerService.getMoreArchives(ownerIdx, reserveIdx);

        return ResponseEntity.ok(ownerArchiveResponseDtos);
    }

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
    public ResponseEntity<List<ReserveArchiveResponseDto>> getAllReserveArchive(@ApiIgnore Authentication authentication){

        Long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        List<ReserveArchiveResponseDto> reserveArchiveResponseDtos = ownerService.getReservedAndArchiving(ownerIdx);

        return ResponseEntity.ok(reserveArchiveResponseDtos);
    }

    @ApiOperation(value = "예약코드 조회", notes = "예약코드 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "예약코드 조회 성공"),
            @ApiResponse(code = 400, message = "예약 및 보관내역 없음"),
            @ApiResponse(code = 401, message = "인증 에러"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{reserveCode}")
    public ResponseEntity<ReserveArchiveInfoResponseDto> readReserveCode(@ApiIgnore Authentication authentication,@PathVariable String reserveCode){

        Long ownerIdx = Long.parseLong((String) authentication.getPrincipal());

        ReserveArchiveInfoResponseDto reserveArchiveInfoResponseDto = ownerService.readReserveCode(ownerIdx,reserveCode);

        return ResponseEntity.ok(reserveArchiveInfoResponseDto);
    }
}
