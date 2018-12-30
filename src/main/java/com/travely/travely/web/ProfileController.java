package com.travely.travely.web;

import com.travely.travely.dto.profile.ProfileDto;
import com.travely.travely.dto.profile.ProfileImgDto;
import com.travely.travely.service.ProfileService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @ApiOperation(value = "프로필 조회", notes = "프로필을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "프로필 조회 성공"),
            @ApiResponse(code = 500, message = "서버 내부 에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("")
    public ResponseEntity findProfile(@ApiIgnore Authentication authentication){

        Long userIdx = Long.parseLong((String) authentication.getPrincipal());
        ProfileDto profileDto = profileService.findUserInfo(userIdx);

        if( profileDto.checkProperties() ) return new ResponseEntity<>(profileDto, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    @ApiOperation(value = "프로필 수정", notes = "프로필을 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "프로필 수정 성공"),
            @ApiResponse(code = 500, message = "서버 내부 에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("")
    public ResponseEntity updateProfile(@ApiIgnore Authentication authentication,
                                        @RequestBody final ProfileDto profileDto){
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());

        return new ResponseEntity<>(profileService.updateProfile(userIdx, profileDto));
    }

    @ApiOperation(value = "프로필 사진 조회", notes = "프로필 사진을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "프로필 사진 조회 성공"),
            @ApiResponse(code = 500, message = "서버 내부 에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/image")
    public ResponseEntity updateProfileImg(@ApiIgnore Authentication authentication){
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());
        String profileImg = profileService.findProfileImg(userIdx);
        return new ResponseEntity<>(profileImg, HttpStatus.OK);
    }


    @ApiOperation(value = "프로필 사진 수정", notes = "프로필 사진을 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "프로필 사진 수정 성공"),
            @ApiResponse(code = 500, message = "서버 내부 에러")
    })
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/image")
    public ResponseEntity updateProfileImg(@ApiIgnore Authentication authentication,
                                           @RequestBody final MultipartFile profileImg){
        Long userIdx = Long.parseLong((String) authentication.getPrincipal());
        ProfileImgDto profileImgDto = new ProfileImgDto(profileImg);
        profileService.updateProfileImg(userIdx, profileImgDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
