package com.travely.travely.dto.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
public class ExceptionResponseDto {
    public static final int NAME_PARSE_INDEX = 8;
    public static final int CODES_FIELD_INDEX = 1;
    public static final String SPLIT_TOKEN = "\\.";

    @ApiModelProperty(example = "phoneNumber", position = 1)
    private String field;

    @ApiModelProperty(example = "잘못된 전화번호 형식입니다.", position = 2)
    private String message;

    public static ExceptionResponseDto toDto(ObjectError validError) {
        return ExceptionResponseDto.builder()
                .message(validError.getDefaultMessage())
                .field(validError.getCodes()[CODES_FIELD_INDEX].split(SPLIT_TOKEN)[CODES_FIELD_INDEX])
                .build();
    }
}
