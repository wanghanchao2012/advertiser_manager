package com.emarbox.dao;

import com.emarbox.dto.AdvertiserInfoQuery;
import com.emarbox.entity.AdvertiserInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class AdvertiserInfoDao extends BaseDao<AdvertiserInfo> {

    public List<AdvertiserInfo> getListByPage(AdvertiserInfoQuery query) {
        return mapperProvider.getAdvertiserInfoMapper().getListByPage(query);
    }

    public Integer getCountByPage(AdvertiserInfoQuery query) {
        return Optional.ofNullable(mapperProvider.getAdvertiserInfoMapper().getCountByPage(query)).orElse(0);
    }

    public List<AdvertiserInfo> getAdvertiserAll() {
        return Optional.ofNullable(mapperProvider.getAdvertiserInfoMapper().selectAll()).orElse(Collections.emptyList());
    }

    public List<AdvertiserInfo> getAdvertiserInfoList(AdvertiserInfo info) {
        return mapperProvider.getAdvertiserInfoMapper().getAdvertiserInfoList(info);
    }


    public AdvertiserInfo getAdvertiserInfo(Long advertiserId) {
        Example example = new Example(AdvertiserInfo.class);
        example.createCriteria().andEqualTo("id",advertiserId);
        return mapperProvider.getAdvertiserInfoMapper().selectOneByExample(example);
    }

    public boolean edit(AdvertiserInfo record) {
        Example example = new Example(AdvertiserInfo.class);
        example.createCriteria().andEqualTo("id",record.getId());
        AdvertiserInfo advertiserInfo = mapperProvider.getAdvertiserInfoMapper().selectOneByExample(example);
        if (advertiserInfo != null) {
            record = beanSupply(advertiserInfo, record, AdvertiserInfo.class);
        }
        return mapperProvider.getAdvertiserInfoMapper().updateByExample(record,example) > 0;
    }


    public boolean add(AdvertiserInfo record) {
        return mapperProvider.getAdvertiserInfoMapper().insertUseGeneratedKeys(record) > 0;
    }

}
