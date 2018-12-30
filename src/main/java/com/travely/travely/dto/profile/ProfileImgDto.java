package com.travely.travely.dto.profile;

import com.travely.travely.domain.ProfileImg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class ProfileImgDto {
    private MultipartFile photo;

    public ProfileImgDto(ProfileImg profileImg){
        this.photo = profileImg.getPhoto();
    }

    public boolean checkProperties(){
        return this.photo != null;
    }
}
