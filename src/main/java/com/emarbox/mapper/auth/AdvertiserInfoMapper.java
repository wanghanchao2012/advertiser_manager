package com.emarbox.mapper.auth;

import com.emarbox.common.BaseMapper;
import com.emarbox.dto.AdvertiserInfoQuery;
import com.emarbox.entity.AdvertiserInfo;

import java.util.List;

public interface AdvertiserInfoMapper extends BaseMapper<AdvertiserInfo> {

    public List<AdvertiserInfo> getListByPage(AdvertiserInfoQuery query);

    public Integer getCountByPage(AdvertiserInfoQuery query);

    public List<AdvertiserInfo> getAdvertiserInfoList(AdvertiserInfo info);
}
