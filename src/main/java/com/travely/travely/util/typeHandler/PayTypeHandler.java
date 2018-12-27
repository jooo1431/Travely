package com.travely.travely.util.typeHandler;

import org.apache.ibatis.type.MappedTypes;

@MappedTypes(PayType.class)
public class PayTypeHandler extends CommonEnumTypeHandler {

    @Override
    CommonEnum getTypeByValue(int value) throws Exception {
        for (PayType payType : PayType.values()) {
            if (payType.getValue() == value)
                return payType;
        }
        //잘못된 value 들어온 경우
        throw new Exception("Wrong value.");
    }
}
