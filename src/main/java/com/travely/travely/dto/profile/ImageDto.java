package com.travely.travely.dto.profile;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImageDto {
    private MultipartFile photo;
    private String classify;
    private String tableName = classify + "Img";
    private int limit;

    public ImageDto(MultipartFile photo, String classify){
        this.photo = photo;
        this.classify = classify;
    }


    public boolean checkClassify(){
        return (this.classify == "profile" || this.classify =="baggage" || this.classify == "inquiry" );
    }

    public boolean checkProperties(){
        return ( this.photo != null & this.classify != null);
    }
}
