package com.emarbox.service;

import com.emarbox.dao.PlatformIndustryInfoDao;
import com.emarbox.dto.common.TreeVo;
import com.emarbox.entity.PlatformIndustryInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PlatformIndustryInfoService {
    
    @Autowired
    PlatformIndustryInfoDao platformIndustryInfoDao;
    
    public List<TreeVo> getIndustryOptions(Long platformId){
        List<TreeVo> optionItems = new ArrayList<>();
        List<PlatformIndustryInfo> platformIndustryInfos = platformIndustryInfoDao.getIndustryInfoList(platformId);
        if(CollectionUtils.isNotEmpty(platformIndustryInfos)){
            Map<Long, List<PlatformIndustryInfo>> industryInfoMap = platformIndustryInfos.stream().filter(e -> Objects.nonNull(e.getParentIndustryId()) && !e.getParentIndustryId().equals(0L)).collect(Collectors.groupingBy(PlatformIndustryInfo::getParentIndustryId));
            List<PlatformIndustryInfo> parentIndustryList = platformIndustryInfos.stream().filter(e -> Objects.isNull(e.getParentIndustryId()) || e.getParentIndustryId().equals(0L)).collect(Collectors.toList());
            for (PlatformIndustryInfo record : parentIndustryList) {
                TreeVo optionItem = new TreeVo();
                optionItem.setValue(record.getIndustryId());
                optionItem.setLabel(record.getIndustryName());
                optionItems.add(optionItem);
                recursionTree(industryInfoMap,optionItem);
            }
        }
        return optionItems;
    }

    private void recursionTree(Map<Long, List<PlatformIndustryInfo>> industryInfoMap,TreeVo optionItem){
        if (industryInfoMap.containsKey(optionItem.getValue())) {
            List<PlatformIndustryInfo> childrenList = industryInfoMap.get(optionItem.getValue());
            for (PlatformIndustryInfo item : childrenList) {
                TreeVo currentOptionItem = TreeVo.builder().value(item.getIndustryId()).label(item.getIndustryName()).build();
                if (CollectionUtils.isEmpty(optionItem.getChildren())) {
                    optionItem.setChildren(new ArrayList<>());
                }
                optionItem.getChildren().add(currentOptionItem);
            }
        }
    }
}
