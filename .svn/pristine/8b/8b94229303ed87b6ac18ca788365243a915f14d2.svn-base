package com.emarbox.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emarbox.entity.PlatformIndustryInfo;
import com.emarbox.mapper.MapperAuthProvider;

import tk.mybatis.mapper.entity.Example;

@Repository
public class PlatformIndustryInfoDao {

    @Autowired
    MapperAuthProvider mapperProvider;

    public List<PlatformIndustryInfo> getIndustryInfoList(Long platformId){
        Example example = new Example(PlatformIndustryInfo.class);
        example.createCriteria().andEqualTo("platformId",platformId);
        return mapperProvider.getPlatformIndustryInfoMapper().selectByExample(example);
    }

}
