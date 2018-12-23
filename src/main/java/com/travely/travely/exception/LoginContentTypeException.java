package com.travely.travely.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginContentTypeException extends AuthenticationException {
    public static final String MESSAGE = "올바른 content-type이 아닙니다.";
    public LoginContentTypeException() {
        super(MESSAGE);
    }
}
