package com.travely.travely.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.travely.travely.auth.UserDetailsImpl;
import com.travely.travely.auth.jwt.JwtInfo;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
public class JwtUtil {

    public static String createToken(UserDetailsImpl userDetailsImpl) {
        return createToken(userDetailsImpl, Date.from(LocalDateTime.now().plusDays(JwtInfo.EXPIRES_LIMIT).toInstant(ZoneOffset.ofHours(9))));
    }

    private static String createToken(UserDetailsImpl userDetailsImpl, Date date) {
        try {
            return JWT.create()
                    .withIssuer(JwtInfo.ISSUER)
                    .withClaim(JwtInfo.USER_EMAIL, userDetailsImpl.getUsername())
                    .withClaim(JwtInfo.USER_AUTHORITY, userDetailsImpl.getAuthorities().toArray()[0].toString())
                    .withExpiresAt(date)
                    .sign(JwtInfo.getAlgorithm());
        } catch (JWTCreationException createEx) {
            return null;
        }
    }

    public static Boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(JwtInfo.getAlgorithm()).build();
            verifier.verify(token);

            return Boolean.TRUE;
        } catch (JWTVerificationException verifyEx) {
            return Boolean.FALSE;
        }
    }

    public static String refreshToken(UserDetailsImpl userDetailsImpl) {
        //todo 토큰 만료시 리프레시 토큰 발급
        return createToken(userDetailsImpl, Date.from(LocalDateTime.now().plusDays(JwtInfo.EXPIRES_LIMIT).toInstant(ZoneOffset.ofHours(9))));
    }

    public static DecodedJWT tokenToJwt(String token) {
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException decodeEx) {
            return null;
        }
    }
}
