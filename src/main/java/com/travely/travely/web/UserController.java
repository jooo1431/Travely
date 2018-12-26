package com.travely.travely.web;

import com.travely.travely.auth.jwt.JwtInfo;
import com.travely.travely.dto.exception.ExceptionResponseDto;
import com.travely.travely.dto.user.LoginUsersRequestDto;
import com.travely.travely.dto.user.UsersSaveRequestDto;
import com.travely.travely.service.UserService;
import com.travely.travely.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @ApiOperation(value = "일반 유저 생성", notes = "일반 유저를 생성합니다. 성공시 jwt 토큰을 헤더에 넣어서 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "유저 생성 성공", response = void.class),
            @ApiResponse(code = 400, message = "유효성 체크 에러 or 이미 가입된 이메일(String 메세지만 출력됩니다.)", response = ExceptionResponseDto.class),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping
    public ResponseEntity<Void> saveUsers(@Validated @RequestBody UsersSaveRequestDto usersSaveRequestDto) {
        String token = JwtUtil.createToken(userService.saveUsers(usersSaveRequestDto));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtInfo.HEADER_NAME, token);

        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).build();
    }

    @ApiOperation(value = "로그인", notes = "로그인합니다. 성공시 jwt 토큰을 헤더에 넣어서 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 403, message = "로그인 실패"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("/login")
    public void login(@RequestBody LoginUsersRequestDto loginUsersRequestDto) {
    }

}
