package com.travely.travely.web;

import com.travely.travely.dto.report.ReportResponseDto;
import com.travely.travely.service.ReportService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    final ReportService reportService;

    @ApiOperation(value = "신고 버튼 누르기", notes = "신고 버튼을 누른다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "신고하기 성공"),
            @ApiResponse(code = 500, message = "서버에러")})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/{reviewIdx}")
    public ResponseEntity<Void> saveReport(@ApiIgnore Authentication authentication,
                                           @PathVariable final Long reviewIdx) {
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());
        ReportResponseDto reportResponseDto = new ReportResponseDto(reviewIdx, userIdx);
        reportService.saveReport(reportResponseDto);
        return ResponseEntity.ok().build();
    }

}
