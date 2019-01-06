package com.travely.travely.mapper;

import com.travely.travely.domain.Report;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReportMapper {

    @Insert("INSERT report( reviewIdx, userIdx ) VALUES(#{report.reviewIdx}, #{report.userIdx})")
    void saveReport(@Param("report") final Report report);
}
