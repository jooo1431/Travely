package com.travely.travely.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class LoginUsersRequestDto {

    @ApiModelProperty(example = "example@gmail.com", position = 1)
    private String email;

    @ApiModelProperty(example = "qwer1234@@", position = 2)
    private String password;

    public UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }
}
