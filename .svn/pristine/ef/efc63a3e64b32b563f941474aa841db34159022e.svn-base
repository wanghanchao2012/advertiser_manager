package com.emarbox.service;

import com.emarbox.dao.DaoProvider;
import com.emarbox.dto.common.AdvertiserOptionItemVo;
import com.emarbox.dto.common.OptionItemVo;
import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseInfoService {

    @Autowired
    DaoProvider daoProvider;
    @Autowired
    Mapper mapper;


    public List<OptionItemVo> preAppendAll(List<?> options) {
        List<OptionItemVo> optionList = new ArrayList<OptionItemVo>();
        optionList.add(OptionItemVo.builder().text("全部").value("").build());
        if (CollectionUtils.isNotEmpty(options)) {
            optionList.addAll(options.stream().map(option -> mapper.map(option, OptionItemVo.class)).collect(Collectors.toList()));
        }
        return optionList;
    }

    public List<AdvertiserOptionItemVo> preAppendAllAdvertiser(List<?> options) {
        List<AdvertiserOptionItemVo> optionList = new ArrayList<AdvertiserOptionItemVo>();
        optionList.add(AdvertiserOptionItemVo.builder().text("全部").value("").build());
        if (CollectionUtils.isNotEmpty(options)) {
            optionList.addAll(options.stream().map(option -> mapper.map(option, AdvertiserOptionItemVo.class)).collect(Collectors.toList()));
        }
        return optionList;
    }
}
