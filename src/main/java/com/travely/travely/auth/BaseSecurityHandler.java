package com.travely.travely.auth;

import com.amazonaws.services.ec2.model.Reservation;
import com.google.gson.Gson;
import com.travely.travely.advice.ValidationExceptionControllerAdvice;
import com.travely.travely.auth.jwt.JwtInfo;
import com.travely.travely.domain.Reserve;
import com.travely.travely.dto.exception.ExceptionResponseDto;
import com.travely.travely.exception.LoginContentTypeException;
import com.travely.travely.mapper.ReservationMapper;
import com.travely.travely.service.ReservationService;
import com.travely.travely.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

    private final ReservationService reservationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(authentication.getName(), new ArrayList<>(authentication.getAuthorities()));

        Long userIdx = Long.parseLong(authentication.getName());

        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setHeader(JwtInfo.HEADER_NAME, JwtUtil.createToken(userDetailsImpl));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(reservationService.getReserveFlagDto(userIdx)));

        log.info("[request end] -> {}", request.getRequestURI());
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
