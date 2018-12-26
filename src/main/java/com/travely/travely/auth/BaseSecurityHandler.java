package com.travely.travely.auth;

import com.google.gson.Gson;
import com.travely.travely.advice.ValidationExceptionControllerAdvice;
import com.travely.travely.auth.jwt.JwtInfo;
import com.travely.travely.dto.exception.ExceptionResponseDto;
import com.travely.travely.exception.LoginContentTypeException;
import com.travely.travely.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class BaseSecurityHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    private final Gson gson;

    private static final String FIELD = "AUTH";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {

        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(authentication.getName(), new ArrayList<>(authentication.getAuthorities()));
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setHeader(JwtInfo.HEADER_NAME, JwtUtil.createToken(userDetailsImpl));
        log.info("[request end] -> {}",request.getRequestURI());
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        log.warn("[BadCredentialsException] credentials exception {}", exception.getMessage());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (exception.getMessage().equals(LoginContentTypeException.MESSAGE)) {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            response.getWriter().print(gson.toJson(ExceptionResponseDto.builder()
                    .field(ValidationExceptionControllerAdvice.FIELD)
                    .message(exception.getMessage())));
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().print(gson.toJson(ExceptionResponseDto.builder()
                    .field(FIELD)
                    .message(exception.getMessage())));
        }
    }

}
