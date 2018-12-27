package com.travely.travely.util.typeHandler;

import org.apache.ibatis.type.MappedTypes;

@MappedTypes(BagType.class)
public class BagTypeHandler extends CommonEnumTypeHandler {

    @Override
    CommonEnum getTypeByValue(int value) throws Exception {
        for (StateType stateType : StateType.values()) {
            if (stateType.getValue() == value)
                return stateType;
        }
        //잘못된 value 들어온 경우
        throw new Exception("Wrong value.");
    }
}
