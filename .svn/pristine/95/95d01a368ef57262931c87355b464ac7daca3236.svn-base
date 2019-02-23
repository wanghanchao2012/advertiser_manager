package com.emarbox.service;

import com.emarbox.dao.DaoProvider;
import com.emarbox.dto.common.OptionItemVo;
import com.emarbox.entity.EmarboxProjectCategory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmarboxProjectCategoryService {
    @Autowired
    DaoProvider daoProvider;

    public List<OptionItemVo> getProjectCategoryOptionList() {
        List<EmarboxProjectCategory> projectCategoryList = daoProvider.getEmarboxProjectCategoryDao().getProjectCategoryList();
        if (CollectionUtils.isNotEmpty(projectCategoryList)) {
            return projectCategoryList.stream().map(e -> OptionItemVo.builder().value(e.getCategoryId()).text(e.getCategoryName()).build()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
