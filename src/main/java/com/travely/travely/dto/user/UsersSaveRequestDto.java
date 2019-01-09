package com.travely.travely.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.travely.travely.domain.Users;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Slf4j
@Getter
public class UsersSaveRequestDto {
    private final static String EMAIL_REGEX = "^[_0-9a-zA-Z-]+@[0-9a-zA-Z]+[.[_0-9a-zA-Z-]+]*$";
    private final static String PASSWORD_REGEX = "^(?=.*?[a-zA-Z])(?=.*?[0-9]).{8,20}$";
    private final static String PHONE_NO_REGEX = "^01[0|1|6-9][0-9]{3,4}[0-9]{4}$";
    public final static String USER_AUTHORITY = "USER";

    @ApiModelProperty(example = "example@gmail.com", position = 1)
    @Pattern(regexp = EMAIL_REGEX, message = "email 형식이 올바르지 않습니다.")
    private String email;

    @ApiModelProperty(example = "qwer1234@@", position = 2)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = PASSWORD_REGEX, message = "password형식이 올바르지 않습니다.")
    private String password;

    @ApiModelProperty(example = "qwer1234@@", position = 3)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String configPassword;

    @ApiModelProperty(example = "최유성", position = 4)
    @Size(min = 1, max = 20, message = "이름을 입력해주세요.")
    private String name;

    @ApiModelProperty(example = "010-0000-0000", position = 5)
    @Pattern(regexp = PHONE_NO_REGEX, message = "잘못된 전화번호 형식입니다.")
    private String phone;

    @AssertTrue(message = "비밀번호 확인과 비밀번호가 일치하지 않습니다.")
    private boolean isEqualConfigPassword() {
        return password.equals(configPassword);
    }

    public Users toEntity(PasswordEncoder passwordEncoder) {
        return Users.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .phone(phone)
                .auth(USER_AUTHORITY)
                .build();
    }

}
