package com.emarbox.dao;

import com.emarbox.entity.TtAdvertiser;
import com.emarbox.mapper.MapperAuthProvider;
import com.emarbox.mapper.MapperDspProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository
public class TtAdvertiserDao {
    @Autowired
    MapperAuthProvider mapperAuthProvider;
    @Autowired
    MapperDspProvider mapperDspProvider;

    public TtAdvertiser getAdvertiser(Long projectId, Long socialUserId) {
        Example example = new Example(TtAdvertiser.class);
        example.createCriteria().andEqualTo("advertiserId", socialUserId).andEqualTo("projectId", projectId);
        List<TtAdvertiser> advertisers = mapperDspProvider.getTtAdvertiserMapper().selectByExample(example);
        return CollectionUtils.isNotEmpty(advertisers) ? advertisers.get(0) : null;
    }

}
