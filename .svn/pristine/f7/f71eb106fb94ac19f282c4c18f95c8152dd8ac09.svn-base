package com.emarbox.dao;

import com.emarbox.entity.ProjectSet;
import com.emarbox.mapper.MapperDspProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Repository
public class BiddingPlatformAdvertiserDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterDspJdbcTemplate;
    @Autowired
    MapperDspProvider mapperDspProvider;

    public void generateProjectSet(Long projectId) {
        Example example = new Example(ProjectSet.class);
        example.createCriteria().andEqualTo("projectId", projectId);
        List<ProjectSet> projectSets = mapperDspProvider.getProjectSetMapper().selectByExample(example);
        if (CollectionUtils.isEmpty(projectSets)) {
            ProjectSet projectSet = new ProjectSet();
            projectSet.setAdvertiserType(1L);
            projectSet.setAuditGrade(1L);
            projectSet.setAutoApi(1L);
            projectSet.setAutoCheckTrade(0L);
            projectSet.setCodeDeploy(0);
            projectSet.setCreateTime(new Date());
            projectSet.setDynamicCreative(0L);
            projectSet.setFrequencyControl(0);
            projectSet.setDynamicMobileCreative(0);
            projectSet.setPointApi(0L);
            projectSet.setSocialGrade(0L);
            projectSet.setProjectId(projectId);
            projectSet.setSocialApi(1L);
            mapperDspProvider.getProjectSetMapper().insert(projectSet);
        }
    }

    public boolean buildGdtProject(Long projectId, Long platformUserId) {
        generateProjectSet(projectId);
        String sql1 = "update gdt_advertiser set project_id = " + projectId + " where uid = " + platformUserId;
        String sql2 = "update project_set set social_user_id = " + platformUserId + " where project_id = " + projectId;
        boolean success1 = namedParameterDspJdbcTemplate.update(sql1, Collections.emptyMap()) > 0;
        boolean success2 = namedParameterDspJdbcTemplate.update(sql2, Collections.emptyMap()) > 0;
        return success1 && success2;
    }

    public boolean buildToutiaoProject(Long projectId, Long platformUserId) {
        String sql = "update tt_advertiser set project_id = " + projectId + " where advertiser_id = " + platformUserId;
        return namedParameterDspJdbcTemplate.update(sql, Collections.emptyMap()) > 0;
    }

    public boolean isExistGdtAdvertiser(Long platformUserId) {
        String sql = "select ga.uid from gdt_advertiser ga where ga.uid = " + platformUserId;
        return isExistAdvertiser(sql);
    }

    public boolean isExistToutiaoAdvertiser(Long platformUserId) {
        String sql = "select ta.advertiser_id from tt_advertiser ta where ta.advertiser_id = " + platformUserId;
        return isExistAdvertiser(sql);
    }

    public boolean isExistAdvertiser(String sql) {
        List<Map<String, Object>> advertiserList = namedParameterDspJdbcTemplate.queryForList(sql, Collections.emptyMap());
        return CollectionUtils.isNotEmpty(advertiserList);
    }

    public boolean existGdtBuildProject(Long platformUserId) {
        String sql = "select ga.project_id from gdt_advertiser ga where ga.uid = " + platformUserId;
        return getBuildProjectInfoByAdvertiserId(sql);
    }

    public boolean existTouTiaoBuildProject(Long platformUserId) {
        String sql = "select ta.project_id from tt_advertiser ta where ta.advertiser_id = " + platformUserId;
        return getBuildProjectInfoByAdvertiserId(sql);
    }

    public boolean getBuildProjectInfoByAdvertiserId(String sql) {
        List<Map<String, Object>> advertiserList = namedParameterDspJdbcTemplate.queryForList(sql, Collections.emptyMap());
        if (CollectionUtils.isNotEmpty(advertiserList)) {
            Object projectId = advertiserList.get(0).get("project_id");
            if (Objects.nonNull(projectId) && Integer.parseInt(projectId.toString()) > 0) {
                return true;
            }
        }
        return false;
    }
}
