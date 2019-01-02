package com.travely.travely.service;

import com.travely.travely.dto.profile.ImageDto;
import com.travely.travely.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final S3FileUploadService s3FileUploadService;
    private final ImageMapper imageMapper;
    /**
     * 사진 조회
     * @param userIdx
     * @return (String)ImageUrl
     */
    public String findImg(final Long userIdx, final String classify){
        if( classify.equals("profile")) {
            String imgUrl = imageMapper.findByUserIdx(userIdx);
            return imgUrl;
        }
        else return "찾을 수 없습니다";
    }

    /**
     * 프로필 사진 수정
     * @param userIdx
     * @param imageDto
     * @return HttpStatus
     */
    @Transactional
    public HttpStatus updateImg(final Long userIdx, final ImageDto imageDto){
        try{
                final String ImageUrl = s3FileUploadService.upload(imageDto.getClassify(), imageDto.getPhoto());

            if( imageDto.checkClassify() ) {
                imageMapper.updateImg(userIdx, ImageUrl);
                return HttpStatus.OK;
            }
            else {
                return HttpStatus.BAD_REQUEST;
            }
        }catch (Exception e){
            throw new RuntimeException("");
        }
    }

}
