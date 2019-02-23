package com.emarbox.service;

import com.emarbox.dto.common.OptionItemVo;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesmanService extends BaseInfoService {

    public List<OptionItemVo> getSalesmanOptionsEdit() {
        ArrayList<OptionItemVo> optionItemVos = Lists.newArrayList(OptionItemVo.builder().value(10001).text("(10001)成晓艳").build(),
                OptionItemVo.builder().value(10002).text("(10002)高小").build());
        return optionItemVos;
    }

    public List<OptionItemVo> getSalesmanOptions() {
        return preAppendAll(getSalesmanOptionsEdit());
    }
}
