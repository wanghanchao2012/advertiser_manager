package com.emarbox.dao;

import com.emarbox.entity.EmarboxProject;
import com.emarbox.entity.EmarboxUser;
import com.emarbox.mapper.MapperAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Objects;

@Repository
public class EmarboxUserDao {
    @Autowired
    MapperAuthProvider mapperAuthProvider;

    public EmarboxUser getEmarboxUser(EmarboxUser record) {
        Example example = new Example(EmarboxUser.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(record.getUserId())) {
            criteria.andEqualTo("userId", record.getUserId());
        }
        EmarboxUser emarboxUser = mapperAuthProvider.getEmarboxUserMapper().selectOneByExample(example);
        return emarboxUser;
    }
}
