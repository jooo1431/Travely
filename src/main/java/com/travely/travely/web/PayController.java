package com.travely.travely.web;

import com.travely.travely.dto.iAmPort.IAmPortRequest;
import com.travely.travely.util.IAmPortTokenUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PayController {


    private final IAmPortTokenUtil iAmPortTokenUtil;

    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/complete")
    public ResponseEntity<String> iAmPortResult(@RequestBody final IAmPortRequest iAmPortRequest) {
        //Request에 uid가 들어있다.
        //발급받은 api키를 이용
        //https://api.iamport.kr/users/getToken 에서 토큰발급후
        //위조여부를 https://api.iamport.kr/payments/$imp_uid 에서 토큰과 함께 위조 확인

        return ResponseEntity.status(HttpStatus.CREATED).body(iAmPortTokenUtil.createToken());
    }

}
