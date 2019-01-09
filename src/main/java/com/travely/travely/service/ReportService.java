package com.travely.travely.service;

import com.travely.travely.domain.Report;
import com.travely.travely.dto.report.ReportResponseDto;
import com.travely.travely.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportMapper reportMapper;

    @Transactional
    public void saveReport(ReportResponseDto reportResponseDto){
        Report report = reportResponseDto.toEntity();
        reportMapper.saveReport(report);
    }
}
