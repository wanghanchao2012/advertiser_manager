package com.emarbox.service;

import com.emarbox.dao.DaoProvider;
import com.emarbox.entity.ProjectCertificate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectCertificateService {


    @Autowired
    DaoProvider daoProvider;

    public boolean insert(ProjectCertificate certificate) {
        return daoProvider.getProjectCertificateDao().insert(certificate);
    }

    public String getCertImageUrl(ProjectCertificate certificate) {
        if (StringUtils.isNoneBlank(certificate.getSignature())) {
            ProjectCertificate query = new ProjectCertificate();
            query.setSignature(certificate.getSignature());
            List<ProjectCertificate> projectCertificateList = daoProvider.getProjectCertificateDao().getProjectCertificateList(query);
            if (CollectionUtils.isNotEmpty(projectCertificateList)) {
                return projectCertificateList.get(0) != null ? projectCertificateList.get(0).getFilePath() : null;
            }
        }
        return null;
    }

}
