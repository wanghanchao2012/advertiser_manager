package com.emarbox.dao;

import com.emarbox.entity.ProjectCertificate;
import com.emarbox.mapper.MapperAuthProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

@Repository
public class ProjectCertificateDao {
    @Autowired
    MapperAuthProvider mapperProvider;

    public boolean insert(ProjectCertificate record) {
        return mapperProvider.getProjectCertificateMapper().insert(record) > 0;
    }

    public boolean insertBatch(List<ProjectCertificate> sourceList) {
        return mapperProvider.getProjectCertificateMapper().insertList(sourceList) > 0;
    }

    public boolean delete(ProjectCertificate record) {
        return mapperProvider.getProjectCertificateMapper().delete(record) > 0;
    }

    public boolean delete(Example record) {
        return mapperProvider.getProjectCertificateMapper().deleteByExample(record) > 0;
    }

    public ProjectCertificate getProjectCertificate(ProjectCertificate query) {
        List<ProjectCertificate> projectCertificateList = getProjectCertificateList(query);
        if (CollectionUtils.isNotEmpty(projectCertificateList)) {
            return projectCertificateList.get(0);
        }
        return null;
    }

    public List<ProjectCertificate> getProjectCertificateList(ProjectCertificate query) {
        Example example = new Example(ProjectCertificate.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(query.getAdvertiserId())) {
            criteria.andEqualTo("advertiserId", query.getAdvertiserId());
        }
        if (Objects.nonNull(query.getFilePath())) {
            criteria.andEqualTo("filePath", query.getFilePath());
        }
        if (Objects.nonNull(query.getSignature())) {
            criteria.andEqualTo("signature", query.getSignature());
        }
        return mapperProvider.getProjectCertificateMapper().selectByExample(example);
    }

}
