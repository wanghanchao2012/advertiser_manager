package com.emarbox.service.validator;

import com.emarbox.dto.common.ResponseResult;
import com.emarbox.dto.common.tuple.Tuple3;
import com.emarbox.enums.ValidType;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ValidationBaseService {

    public Tuple3<Boolean, String, ValidType> getTuple3(Boolean isOk, String msg, ValidType vtype) {
        return Tuple3.<Boolean, String, ValidType> builder().v1(isOk).v2(msg).v3(vtype).build();
    }

    public Tuple3<Boolean, String, ValidType> getTuple3(Boolean isOk, String msg) {
        return Tuple3.<Boolean, String, ValidType> builder().v1(isOk).v2(msg).v3(ValidType.ALL).build();
    }

    public void getResponseResult(ValidType validType, List<Tuple3<Boolean, String, ValidType>> errorMsgList, ResponseResult<Map<String, Object>> stringResponseResult) {
        errorMsgList = errorMsgList.stream().filter(validItem -> validItem.getV3().equals(validType) || validItem.getV3().equals(ValidType.ALL)).collect(Collectors.toList());
        List<Tuple3<Boolean, String, ValidType>> errorList = errorMsgList.stream().filter(e -> e.getV1().equals(Boolean.FALSE)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errorList)) {
            stringResponseResult.forFail(-1, errorList.get(0).getV2());
        } else {
            stringResponseResult.forOk();
        }
    }

}