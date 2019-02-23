package com.emarbox.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emarbox.dao.balance.AdvertiserBalanceDao;
import com.emarbox.dao.balance.AgentBalanceDao;
import com.emarbox.dao.balance.ProjectBalanceDao;

import lombok.Data;

@Data
@Repository
public class DaoProvider {
    @Autowired
    AdvertiserInfoDao advertiserInfoDao;
    @Autowired
    PlatformIndustryInfoDao platformIndustryInfoDao;
    @Autowired
    AgentInfoDao agentInfoDao;
    @Autowired
    EmarboxProjectCategoryDao emarboxProjectCategoryDao;
    @Autowired
    ProjectCertificateDao projectCertificateDao;
    @Autowired
    BiddingPlatformAdvertiserDao biddingPlatformAdvertiserDao;
    @Autowired
    EmarboxProjectV2Dao emarboxProjectV2Dao;
    @Autowired
    ProjectSetDao projectSetDao;
    @Autowired
    EmarboxUserDao emarboxUserDao;
    @Autowired
    TtAdvertiserDao ttAdvertiserDao;
    
    @Autowired
    AdvertiserBalanceDao advertiserBalanceDao;
    @Autowired
    AgentBalanceDao agentBalanceDao;
    @Autowired
    ProjectBalanceDao projectBalanceDao;
}
