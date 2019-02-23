package com.emarbox.service;

import com.emarbox.dto.common.OptionItemVo;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService extends BaseInfoService {

    public List<OptionItemVo> getDepartmentOptionsEdit() {
        ArrayList<OptionItemVo> optionItemVos = Lists.newArrayList(OptionItemVo.builder().value(10001).text("(10001)移动一部").build(),
                OptionItemVo.builder().value(10002).text("(10002)移动二部").build());
        return optionItemVos;
    }

    public List<OptionItemVo> getDepartmentOptions() {
        return preAppendAll(getDepartmentOptionsEdit());
    }
}
