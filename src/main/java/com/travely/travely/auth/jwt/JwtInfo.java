package com.travely.travely.auth.jwt;

import com.auth0.jwt.algorithms.Algorithm;

import java.io.UnsupportedEncodingException;

public class JwtInfo {

    public static final String HEADER_NAME = "jwt";

    public static final String ISSUER = "dmzing";

    public static final String TOKEN_KEY = "cys";

    public static final Long EXPIRES_LIMIT = 100L;

    public static final String USER_EMAIL = "email";

    public static final String USER_AUTHORITY = "authority";

    public static Algorithm getAlgorithm() {
        try {
            return Algorithm.HMAC256(JwtInfo.TOKEN_KEY);
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            return Algorithm.none();
        }
    }
}
