package com.travely.travely.dto.profile;

import com.travely.travely.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class ImageDto {
    private MultipartFile photo;
    private String classify;


    public boolean checkProperties(){
        return ( this.photo != null & this.classify != null);
    }
}
