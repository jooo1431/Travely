package com.travely.travely.exception;

public class AlreadyExistsEmailException extends RuntimeException {
    public AlreadyExistsEmailException() {
        super("이미 가입된 이메일입니다.");
    }
}
