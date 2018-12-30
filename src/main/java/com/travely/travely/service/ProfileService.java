package com.travely.travely.service;

import com.travely.travely.domain.Profile;
import com.travely.travely.dto.profile.ProfileDto;
import com.travely.travely.dto.profile.ProfileImgDto;
import com.travely.travely.mapper.ProfileImgMapper;
import com.travely.travely.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileMapper profileMapper;
    private final ProfileImgMapper profileImgMapper;
    private final S3FileUploadService s3FileUploadService;

    /**
     * userIdx로 user 정보 얻어오기
     * @param userIdx
     * @return profileDto
     */
    public ProfileDto findUserInfo(final Long userIdx){
        Profile profile = profileMapper.findByUserIdx(userIdx);
        ProfileDto profileDto = new ProfileDto(profile);
        return profileDto;
    }

    /**
     * 프로필 정보 수정
     * @param userIdx
     * @param profileDto
     * @return HttpStatus
     */
    @Transactional
    public HttpStatus updateProfile(final Long userIdx, final ProfileDto profileDto){
        if(profileDto.checkProperties()) {
            Profile profile = profileDto.toEntity();
            profileMapper.updateProfile(userIdx, profile);
            return HttpStatus.OK;
        }
        else return HttpStatus.BAD_REQUEST;
    }

    /**
     * 프로필 사진 조회
     * @param userIdx
     * @return (String)ImageUrl
     */
    public String findProfileImg(final Long userIdx){
        String profileImg = profileImgMapper.findByUserIdx(userIdx);
        return profileImg;
    }


    /**
     * 프로필 사진 수정
     * @param userIdx
     * @param profileImgDto
     * @return HttpStatus
     */
    @Transactional
    public HttpStatus updateProfileImg(final Long userIdx, final ProfileImgDto profileImgDto){
        try{
                final String profileImgUrl = s3FileUploadService.upload(profileImgDto.getPhoto());
                profileImgMapper.updateProfileImg(userIdx, profileImgUrl);
                return HttpStatus.OK;
            }catch (Exception e){
                throw new RuntimeException("");
            }
    }


}
