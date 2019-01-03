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

//    @ApiOperation(value = "사진 조회", notes = "사진을 조회한다")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "사진 조회 성공"),
//            @ApiResponse(code = 500, message = "서버에러")})
//    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
//    @GetMapping("/{classify}")
//    public ResponseEntity findImage(@ApiIgnore Authentication authentication,
//                                    @PathVariable() final String classify) {
//        Long userIdx = Long.parseLong((String) authentication.getPrincipal());
//        String profileImg = imageService.findImg(userIdx, classify);
//        return new ResponseEntity<>(profileImg, HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "사진 수정", notes = "사진을 수정합니다")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "사진 수정 성공"),
//            @ApiResponse(code = 500, message = "서버 내부 에러")
//    })
//    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
//    @PutMapping("/{classify}")
//    public ResponseEntity updateImage(@ApiIgnore Authentication authentication,
//                                      @RequestBody final MultipartFile image,
//                                      @PathVariable() final String classify){
//        Long userIdx = Long.parseLong((String) authentication.getPrincipal());
//        ImageDto imageDto = new ImageDto(image, classify);
//        imageService.updateImg(userIdx, imageDto);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


    @ApiOperation(value = "사진 등록", notes = "이미지를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "저장 성공"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 에러"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/")
    public ResponseEntity<String> uploadImage(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(s3Util.upload(multipartFile, "/"));
    }
}
