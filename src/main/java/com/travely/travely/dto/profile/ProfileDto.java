package com.travely.travely.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.travely.travely.domain.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class ProfileDto {
    private final static String EMAIL_REGEX = "^[_0-9a-zA-Z-]+@[0-9a-zA-Z]+[.[_0-9a-zA-Z-]+]*$";
    private final static String PASSWORD_REGEX = "^(?=.*?[a-zA-Z])(?=.*?[0-9]).{8,20}$";
    private final static String PHONE_NO_REGEX = "^01[0|1|6-9]-[0-9]{3,4}-[0-9]{4}$";

    @ApiModelProperty(example = "example@gmail.com", position = 1)
    @Pattern(regexp = EMAIL_REGEX, message = "email 형식이 올바르지 않습니다.")
    private String email;

    @ApiModelProperty(example = "qwer1234@@", position = 2)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = PASSWORD_REGEX, message = "password형식이 올바르지 않습니다.")
    private String password;

    @ApiModelProperty(example = "최유성", position = 4)
    @Size(min = 1, max = 20, message = "이름을 입력해주세요.")
    private String name;

    @ApiModelProperty(example = "010-0000-0000", position = 5)
    @Pattern(regexp = PHONE_NO_REGEX, message = "잘못된 전화번호 형식입니다.")
    private String phone;

    @Builder
    public ProfileDto(Profile profile){
        this.email = profile.getEmail();
        this.name = profile.getName();
        this.phone = profile.getPhone();
    }

    public boolean checkProperties(){
        return ( this.email != null & this.name != null & this.phone != null);
    }

    public Profile toEntity(){
        return Profile.builder()
                .email(email)
                .name(name)
                .phone(phone)
                .build();

    }
}
