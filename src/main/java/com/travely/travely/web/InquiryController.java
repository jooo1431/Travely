package com.travely.travely.web;

import com.travely.travely.dto.inquiry.InquiryDto;
import com.travely.travely.service.InquiryService;
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
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    final InquiryService inquiryService;

    @ApiOperation(value = "문의사항 조회", notes = "문의 사항을 조회한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "문의사항 조회 성공"),
            @ApiResponse(code = 500, message = "서버에러")})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{inquiryIdx}")
    public ResponseEntity findInquiryIdx(@ApiIgnore Authentication authentication,
                                         @PathVariable final Long inquiryIdx){
        String inquiryContent = inquiryService.findInquiry(inquiryIdx);
        return new ResponseEntity<>(inquiryContent, HttpStatus.OK);
    }


    @ApiOperation(value = "문의사항 작성", notes = "문의 사항을 작성한다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "문의사항 작성 성공"),
            @ApiResponse(code = 500, message = "서버에러")})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("")
    public ResponseEntity saveInquiry(@ApiIgnore Authentication authentication,
                                      @RequestBody final InquiryDto inquiryDto) {
        Long userIdx = Long.parseLong((String)authentication.getPrincipal());
        return new ResponseEntity<>(inquiryService.saveInquiry(userIdx, inquiryDto));
    }


}
