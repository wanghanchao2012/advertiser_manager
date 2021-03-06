package com.emarbox.dao;

import com.emarbox.entity.EmarboxProjectCategory;
import com.emarbox.mapper.MapperAuthProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmarboxProjectCategoryDao {

    @Autowired
    MapperAuthProvider mapperProvider;

    public List<EmarboxProjectCategory> getProjectCategoryList() {
        List<EmarboxProjectCategory> emarboxProjectCategories = mapperProvider.getEmarboxProjectCategoryMapper().selectAll();
        if (CollectionUtils.isNotEmpty(emarboxProjectCategories)) {
            emarboxProjectCategories = emarboxProjectCategories.stream().sorted((t0, t1) -> t0.getDisplayOrder().compareTo(t1.getDisplayOrder())).collect(Collectors.toList());
        }
        return emarboxProjectCategories;
    }

}
