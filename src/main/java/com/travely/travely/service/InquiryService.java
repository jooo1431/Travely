package com.travely.travely.service;

import com.travely.travely.domain.Inquiry;
import com.travely.travely.dto.inquiry.InquiryDto;
import com.travely.travely.mapper.InquiryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryService {
    final InquiryMapper inquiryMapper;

    /**
     * 문의 사항 저장하기
     *
     * @param userIdx
     * @param inquiryDto
     */
    @Transactional
    public void saveInquiry(final Long userIdx, final InquiryDto inquiryDto) {
        Inquiry inquiry = inquiryDto.toEntity();
        Long inquiryIdx = inquiryMapper.saveInquiry(userIdx, inquiry);

        for (String ImgUrl : inquiryDto.getInquiryImgs()) {
            inquiryMapper.saveInquiryImg(inquiryIdx, ImgUrl);
        }
    }
}
