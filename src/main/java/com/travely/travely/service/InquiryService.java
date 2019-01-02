package com.travely.travely.service;

import com.travely.travely.domain.Inquiry;
import com.travely.travely.dto.inquiry.InquiryDto;
import com.travely.travely.mapper.InquiryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
     * @param inquiryDto
     * @return HttpStatus
     */
    @Transactional
    public HttpStatus saveInquiry(final Long userIdx, final InquiryDto inquiryDto) {
        if (inquiryDto.checkProperties()) {
            Inquiry inquiry = inquiryDto.toEntity();
            inquiryMapper.saveInquiry(userIdx, inquiry);
            return HttpStatus.OK;
        } else
            return HttpStatus.BAD_REQUEST;
    }

    public Inquiry findInquiry(final Long inquiryIdx) {
        return inquiryMapper.findByInquiryIdx(inquiryIdx);
    }
}
