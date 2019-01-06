package com.travely.travely.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.travely.travely.auth.BaseSecurityHandler;
import com.travely.travely.auth.ajax.AjaxAuthenticationProvider;
import com.travely.travely.auth.ajax.filter.AjaxAuthenticationFilter;
import com.travely.travely.auth.jwt.JwtAuthenticationProvider;
import com.travely.travely.auth.jwt.filter.JwtAuthenticationFilter;
import com.travely.travely.auth.jwt.matcher.SkipPathRequestMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationProvider jwtProvider;

    private final AjaxAuthenticationProvider ajaxProvider;

    private final BaseSecurityHandler securityHandler;

    private final ObjectMapper objectMapper;

    private static final String USER_ENTRY_POINT = "/api/users";
    private static final String LOGIN_ENTRY_POINT = "/api/users/login";
    private static final String ERROR_ENTRY_POINT = "/error";
    private static final List<String> swaggersConfig = new ArrayList<>(
            Arrays.asList("/v2/api-docs", "/configuration/ui", "/swagger-resources",
                    "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui",
                    "/swagger-resources/configuration/security")
    );

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(swaggersConfig.toArray(new String[swaggersConfig.size()]));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxProvider)
                .authenticationProvider(jwtProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), FilterSecurityInterceptor.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(ERROR_ENTRY_POINT).permitAll()
                .and()
                .headers().frameOptions().sameOrigin();
    }

    @Bean
    public AntPathRequestMatcher antPathRequestMatcher() {
        return new AntPathRequestMatcher(LOGIN_ENTRY_POINT, HttpMethod.POST.name());
    }

    @Bean
    public AjaxAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
        AjaxAuthenticationFilter filter = new AjaxAuthenticationFilter(antPathRequestMatcher(), objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(securityHandler);
        filter.setAuthenticationFailureHandler(securityHandler);
        return filter;
    }

    @Bean
    public SkipPathRequestMatcher skipPathRequestMatcher() {
        List<String> skipPathList = new ArrayList<>(Arrays.asList(USER_ENTRY_POINT, LOGIN_ENTRY_POINT, ERROR_ENTRY_POINT));
        skipPathList.addAll(swaggersConfig);
        return new SkipPathRequestMatcher(skipPathList);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(skipPathRequestMatcher());
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationFailureHandler(securityHandler);
        return filter;
    }
}