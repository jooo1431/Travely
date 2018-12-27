package com.travely.travely.util.typeHandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public abstract class CommonEnumTypeHandler implements TypeHandler<CommonEnum> {
    // DB에 쓰는 부분
    @Override
    public void setParameter(PreparedStatement ps, int i, CommonEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    // DB에서 읽는 부분
    @Override
    public CommonEnum getResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);

        return getTypeByValueWithCatch(value);
    }

    // DB에서 읽는 부분
    @Override
    public CommonEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);

        return getTypeByValueWithCatch(value);
    }

    // DB에서 읽는 부분
    @Override
    public CommonEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);

        return getTypeByValueWithCatch(value);
    }

    // Exception 발생시 처리
    private CommonEnum getTypeByValueWithCatch(int value) {
        CommonEnum commonEnum = null;

        try {
            commonEnum = getTypeByValue(value);
        } catch (Exception e) {
            log.error("Fail to get result from DB (Unknown)", e);
        }

        return commonEnum;
    }

    // 각 Enum 타입 핸들러 마다 구현해야 할 메소드
    abstract CommonEnum getTypeByValue(int value) throws Exception;

}
