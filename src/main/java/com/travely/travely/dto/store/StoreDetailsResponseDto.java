package com.travely.travely.dto.store;

import com.travely.travely.dto.review.ReviewResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;


@Getter
public class StoreDetailsResponseDto {

    private StoreDetailsInfoResonseDto storeDetailsInfoResonseDto;

    private List<ReviewResponseDto> reviewResponseDto;

    private Double grade;

    public StoreDetailsResponseDto(StoreDetailsInfoResonseDto storeDetailsInfoResonseDto, List<ReviewResponseDto> reviewResponseDto) {
        this.reviewResponseDto = reviewResponseDto;
        this.storeDetailsInfoResonseDto = storeDetailsInfoResonseDto;
        this.grade = reviewResponseDto.stream().mapToDouble(ReviewResponseDto::getLike).average().getAsDouble();
    }
}
