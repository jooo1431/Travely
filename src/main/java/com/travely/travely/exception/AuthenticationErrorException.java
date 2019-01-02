package com.travely.travely.exception;

public class AuthenticationErrorException extends RuntimeException {
    public AuthenticationErrorException() {
        super("인증 에러.");
    }
}
