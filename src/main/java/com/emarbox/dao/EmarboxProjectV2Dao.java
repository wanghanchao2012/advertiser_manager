package com.emarbox.dao;

import com.emarbox.dto.ProjectInfoQuery;
import com.emarbox.entity.EmarboxProject;
import com.emarbox.entity.ProjectSet;
import com.emarbox.entity.TtAdvertiser;
import com.emarbox.enums.PlatformType;
import com.emarbox.mapper.MapperAuthProvider;
import com.emarbox.mapper.MapperDspProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class EmarboxProjectV2Dao {

    @Autowired
    MapperAuthProvider mapperProvider;
    @Autowired
    MapperDspProvider mapperDspProvider;
    @Autowired
    @Qualifier(value="namedParameterAuthJdbcTemplate")
    NamedParameterJdbcTemplate namedParameterAuthJdbcTemplate;

    public List<EmarboxProject> getListByPage(ProjectInfoQuery query) {
        return mapperProvider.getEmarboxProjectMapper().getListByPage(query);
    }

    public Integer getCountByPage(ProjectInfoQuery query) {
        return Optional.ofNullable(mapperProvider.getEmarboxProjectMapper().getCountByPage(query)).orElse(0);
    }

    public EmarboxProject getProjectInfo(Long projectId) {
        EmarboxProject projectInfo = mapperProvider.getEmarboxProjectMapper().getProjectInfo(EmarboxProject.builder().id(projectId).build());
        settingSocialUserId(projectId, projectInfo);
        if (Objects.nonNull(projectInfo.getPlatformId())) {
            Map<Integer, String> platformMap = EnumSet.allOf(PlatformType.class).stream().collect(Collectors.toMap(e -> e.getIndex(), e -> e.getDesc()));
            projectInfo.setPlatformName(platformMap.get(projectInfo.getPlatformId()));
        }
        return projectInfo;
    }

    private void settingSocialUserId(Long projectId, EmarboxProject emarboxProject) {
        if (emarboxProject != null && Objects.nonNull(emarboxProject.getPlatformId())) {
            if (emarboxProject.getPlatformId().equals(PlatformType.GDT.getIndex())) {
                Example example = new Example(ProjectSet.class);
                example.createCriteria().andEqualTo("projectId", projectId);
                List<ProjectSet> projectSets = mapperDspProvider.getProjectSetMapper().selectByExample(example);
                if (CollectionUtils.isNotEmpty(projectSets)) {
                    ProjectSet projectSet = projectSets.get(0);
                    if (projectSet != null) {
                        emarboxProject.setSocialUserId(projectSet.getSocialUserId());
                    }
                }
            } else if (emarboxProject.getPlatformId().equals(PlatformType.TOU_TIAO.getIndex())) {
                Example example = new Example(TtAdvertiser.class);
                example.createCriteria().andEqualTo("projectId", projectId);
                List<TtAdvertiser> ttAdvertisers = mapperDspProvider.getTtAdvertiserMapper().selectByExample(example);
                if (CollectionUtils.isNotEmpty(ttAdvertisers)) {
                    TtAdvertiser advertiser = ttAdvertisers.get(0);
                    if (advertiser != null) {
                        emarboxProject.setSocialUserId(advertiser.getAdvertiserId());
                    }
                }
            }
        }
    }

    public List<EmarboxProject> getProjectAll() {
        return mapperProvider.getEmarboxProjectMapper().selectAll();
    }

    public boolean edit(EmarboxProject record) {
        Example example = new Example(EmarboxProject.class);
        example.createCriteria().andEqualTo("id", record.getId());
        return mapperProvider.getEmarboxProjectMapper().updateByExample(record, example) > 0;
    }


    public EmarboxProject add(EmarboxProject record) {
        mapperProvider.getEmarboxProjectMapper().insertUseGeneratedKeys(record);
        return record;
    }


    public boolean sampleEdit(EmarboxProject emarboxProject) {
        SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(emarboxProject);
        return namedParameterAuthJdbcTemplate.update(updateEmarboxProjectSql(), sqlParam) > 0;
    }

    public String updateEmarboxProjectSql() {
        StringBuffer sql = new StringBuffer();
        sql.append(" update emarbox_project p set  ").append("\n");
        sql.append(" p.project_name = :projectName ").append("\n");
        sql.append(" ,p.project_url = :projectUrl ").append("\n");
        sql.append(" ,p.linkman = :linkman ").append("\n");
        sql.append(" ,p.mobile = :mobile ").append("\n");
        sql.append(" ,p.email = :email ").append("\n");
        sql.append(" ,p.related_qq = :relatedQq ").append("\n");
        sql.append(" ,p.update_time = :updateTime ").append("\n");
        sql.append(" ,p.update_user = :updateUser ").append("\n");
        sql.append(" ,p.pig_advertiser_id = :advertiserId ").append("\n");
        sql.append(" ,p.pig_salesman_id = :salesmanId ").append("\n");
        sql.append(" ,p.pig_department_id = :departmentId ").append("\n");
        sql.append(" ,p.pig_license_code = :licenseCode ").append("\n");
        sql.append(" ,p.pig_platform_id = :platformId ").append("\n");
        sql.append(" ,p.pig_category_type = :categoryType ").append("\n");
        sql.append(" ,p.pig_sub_category_type = :subCategoryType ").append("\n");
        sql.append(" where p.project_id = :id ");
        return sql.toString();
    }

}
