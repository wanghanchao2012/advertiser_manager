package com.emarbox.service.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.emarbox.dto.common.ResponseResult;
import com.emarbox.dto.common.tuple.Tuple3;
import com.emarbox.entity.AdvertiserInfo;
import com.emarbox.enums.ValidType;
@Service
public class AdvertiserValidatorService extends ValidationBaseService {

    public ResponseResult<Map<String, Object>> fromValidator(ValidType validType, AdvertiserInfo advertiserInfo) {

        List<Tuple3<Boolean, String, ValidType>> errorMsgList = new ArrayList<>();
        errorMsgList.add(getTuple3(Objects.nonNull(advertiserInfo.getAdvertiserName()), "广告主名称不能为空"));
        errorMsgList.add(getTuple3(Objects.nonNull(advertiserInfo.getAgentId()), "代理商不能为空"));
        errorMsgList.add(getTuple3(Objects.nonNull(advertiserInfo.getCreditCode()), "统一社会信用代码不能为空"));
        errorMsgList.add(getTuple3(Objects.nonNull(advertiserInfo.getCompanyWebsite()), "公司网站不能为空"));
        errorMsgList.add(getTuple3(Objects.nonNull(advertiserInfo.getCategoryType()), "所属行业不能为空"));
        errorMsgList.add(getTuple3(CollectionUtils.isNotEmpty(advertiserInfo.getCertificateSource())
                && advertiserInfo.getCertificateSource().size() == 4
                && advertiserInfo.getCertificateSource().stream().allMatch(e->Objects.nonNull(e.getImgPath()) && Objects.nonNull(e.getCode())),
                "不完整的基础资质"));
        errorMsgList.add(getTuple3(Objects.nonNull(advertiserInfo.getCompanyWebsite()) && advertiserInfo.getCompanyWebsite().length() < 200, "公司网站不能超过200个字符"));
        errorMsgList.add(getTuple3(Objects.nonNull(advertiserInfo.getAdvertiserName()) && advertiserInfo.getAdvertiserName().length() < 40, "广告主名称不能超过40个字符"));
        if(Objects.nonNull(advertiserInfo.getLinkMan()))
        errorMsgList.add(getTuple3(Objects.nonNull(advertiserInfo.getLinkMan()) && advertiserInfo.getLinkMan().length() < 20, "联系人不能超过20个字符"));
        if(Objects.nonNull(advertiserInfo.getPhoneNumber()))
        errorMsgList.add(getTuple3(Objects.nonNull(advertiserInfo.getPhoneNumber()) && advertiserInfo.getPhoneNumber().length() < 20, "电话/手机不能超过20个字符"));

        ResponseResult<Map<String, Object>> stringResponseResult = new ResponseResult<>();
        getResponseResult(validType, errorMsgList, stringResponseResult);
        return stringResponseResult;
    }

}
