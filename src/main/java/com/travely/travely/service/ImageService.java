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
            if( imageDto.getClassify().equals("profile")) {
                final String profileImgUrl = s3FileUploadService.upload(imageDto.getClassify(), imageDto.getPhoto());
                imageMapper.updateProfileImg(userIdx, profileImgUrl);
                return HttpStatus.OK;
            }
//            else if(imageDto.getClassify().equals("baggage")){
//                final String baggageImgUrl = s3FileUploadService.upload(imageDto.getClassify(), imageDto.getPhoto());
//                imageMapper.updateBaggageImg(userIdx, baggageImgUrl);
//                return HttpStatus.OK;
//            }
//            else if(imageDto.getClassify().equals("inquiry")){
//                final String inquiryImgUrl = s3FileUploadService.upload(imageDto.getClassify(), imageDto.getPhoto());
//                imageMapper.updateInquiryImg(userIdx, inquiryImgUrl);
//                return HttpStatus.OK;
//            }
            else {
                return HttpStatus.BAD_REQUEST;
            }
        }catch (Exception e){
            throw new RuntimeException("");
        }
    }

}
