package com.travely.travely.util.typeHandler;

import org.apache.ibatis.type.MappedTypes;

@MappedTypes(ProgressType.class)
public class ProgressTypeHandler extends CommonEnumTypeHandler {

    @Override
    CommonEnum getTypeByValue(int value) throws Exception {
        for (ProgressType progressType : ProgressType.values()) {
            if (progressType.getValue() == value)
                return progressType;
        }
        //잘못된 value 들어온 경우
        throw new Exception("Wrong value.");
    }
}
