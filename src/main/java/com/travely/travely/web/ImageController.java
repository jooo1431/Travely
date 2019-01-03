package com.travely.travely.web;


import com.travely.travely.service.ImageService;
import com.travely.travely.util.S3Util;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/img")
@RequiredArgsConstructor
public class ImageController {
    final ImageService imageService;

    private final S3Util s3Util;

    @ApiOperation(value = "사진 등록", notes = "이미지를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "저장 성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 에러"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping()
    public ResponseEntity<String> uploadImage(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(s3Util.upload(multipartFile, "/"));
    }
}
