package com.emarbox.service;

import com.emarbox.dto.ProjectInfoQuery;
import com.emarbox.dto.common.OptionItemVo;
import com.emarbox.dto.common.ResponseResult;
import com.emarbox.entity.*;
import com.emarbox.enums.ErrorInfos;
import com.emarbox.enums.StatusType;
import com.emarbox.mapper.MapperAuthProvider;
import com.emarbox.service.validator.ProjectInfoValidatorService;
import com.emarbox.util.AliasUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmarboxProjectV2Service extends BaseInfoService {

    @Autowired
    MapperAuthProvider mapperAuthProvider;

    @Autowired
    ProjectInfoValidatorService projectInfoValidatorService;

    public ResponseResult<List<EmarboxProject>> getPage(ProjectInfoQuery query) {
        ResponseResult.ResponseResultBuilder<List<EmarboxProject>> builder = ResponseResult.builder();
        List<EmarboxProject> listByPage = Collections.emptyList();
        Integer pageSize = daoProvider.getEmarboxProjectV2Dao().getCountByPage(query);
        if (pageSize.intValue() > 0) {
            query.getPage().setRecordCount(pageSize);
            listByPage = this.daoProvider.getEmarboxProjectV2Dao().getListByPage(query);
        }
        builder.data(listByPage).page(query.getPage());
        return builder.build().forOk();
    }

    public EmarboxProject getProjectInfo(Long projectId) {
        return daoProvider.getEmarboxProjectV2Dao().getProjectInfo(projectId);
    }

    @Cacheable(value = "getProjectOptions" )
    public List<OptionItemVo> getProjectOptions() {
        List<OptionItemVo> optionItemVoList = Collections.emptyList();
        List<EmarboxProject> projectAll = daoProvider.getEmarboxProjectV2Dao().getProjectAll();
        if (CollectionUtils.isNotEmpty(projectAll)) {
            optionItemVoList = projectAll.stream().map(e -> OptionItemVo.builder().value(e.getId()).text(e.getProjectName()).realValue(e.getProjectName()).build().preAppendValue()).collect(Collectors.toList());
        }
        return preAppendAll(optionItemVoList);
    }


    public ResponseResult<Map<String, Object>> edit(EmarboxProject emarboxProject) {
        ResponseResult<Map<String, Object>> result = new ResponseResult<>();
        if (Objects.isNull(emarboxProject.getId())) {
            return result.forFail(ErrorInfos.MISSING_REQUIRED_PARAM);
        }
        if (Objects.nonNull(emarboxProject.getSocialUserId())) {
            boolean isContinue = false;
            if (emarboxProject.getPlatformId().equals(2)) {
                ProjectSet projectSet = daoProvider.getProjectSetDao().getProjectSet(emarboxProject.getId(), emarboxProject.getSocialUserId());
                if (projectSet == null || !projectSet.getProjectId().equals(emarboxProject.getId())) {
                    isContinue = true;
                }
            } else if (emarboxProject.getPlatformId().equals(3)) {
                TtAdvertiser advertiser = daoProvider.getTtAdvertiserDao().getAdvertiser(emarboxProject.getId(), emarboxProject.getSocialUserId());
                if (advertiser == null || !advertiser.getProjectId().equals(emarboxProject.getId())) {
                    isContinue = true;
                }
            }
            if (isContinue) {
                projectInfoValidatorService.advertiserBindingValidator(emarboxProject, result);
                if (!result.getCode().equals(0)) {
                    return result;
                }
            }
        }
        operatorEmarboxProject(false, emarboxProject);
        return result.forOk();
    }


    public ResponseResult<Map<String, Object>> add(EmarboxProject emarboxProject) {
        ResponseResult<Map<String, Object>> result = new ResponseResult<>();
        if (Objects.nonNull(emarboxProject.getSocialUserId())) {
            projectInfoValidatorService.advertiserBindingValidator(emarboxProject, result);
            if (!result.getCode().equals(0)) {
                return result;
            }
        }
        operatorEmarboxProject(true, emarboxProject);
        return result.forOk();
    }

    @Transactional
    public void operatorEmarboxProject(boolean isAdd, EmarboxProject emarboxProject) {
        buildEmarboxProjectBean(false, emarboxProject);
        if (isAdd == false) {
            daoProvider.getEmarboxProjectV2Dao().sampleEdit(emarboxProject);
        } else {
            daoProvider.getEmarboxProjectV2Dao().add(emarboxProject);
            projectUserRelation(emarboxProject);
        }
        saveProjectCode(emarboxProject);
        bindPlatformUserId(emarboxProject);
    }


    private void buildEmarboxProjectBean(boolean isAdd, EmarboxProject emarboxProject) {
        EmarboxUser emarboxUser = null;
        if (Objects.nonNull(emarboxProject.getUserId())) {
            emarboxUser = daoProvider.getEmarboxUserDao().getEmarboxUser(EmarboxUser.builder().userId(emarboxProject.getUserId()).build());
        }
        String userType = emarboxUser == null ? "sub_user" : emarboxUser.getUserType();
        Long userId = Optional.ofNullable(emarboxUser == null ? null : emarboxUser.getUserId()).orElse(141L);//当用户为空时，默认易博用户admin@emar.com.cn

        AdvertiserInfo advertiserInfo = daoProvider.getAdvertiserInfoDao().getAdvertiserInfo(emarboxProject.getAdvertiserId());
        if (advertiserInfo != null) {
            emarboxProject.setLinkman(advertiserInfo.getLinkMan());
            emarboxProject.setMobile(advertiserInfo.getPhoneNumber());
            emarboxProject.setProjectUrl(advertiserInfo.getCompanyWebsite());
        }
        emarboxProject.setDeleted(0L);
        if (isAdd) {
            emarboxProject.setCreateTime(new Date());
            emarboxProject.setCreateUser("advertiser_manager");
            emarboxProject.setStatus(StatusType.pending.name());
        } else {
            emarboxProject.setUpdateTime(new Date());
            emarboxProject.setUpdateUser("advertiser_manager");
        }
        emarboxProject.setUserType(userType);
        emarboxProject.setSiteTypeCode("independent");
        emarboxProject.setUserId(userId);
    }

    private void bindPlatformUserId(EmarboxProject emarboxProject) {
        if (Objects.nonNull(emarboxProject.getSocialUserId())) {
            if (emarboxProject.getPlatformId().equals(2)) {
                daoProvider.getBiddingPlatformAdvertiserDao().buildGdtProject(emarboxProject.getId(), emarboxProject.getSocialUserId());
            } else if (emarboxProject.getPlatformId().equals(3)) {
                daoProvider.getBiddingPlatformAdvertiserDao().buildToutiaoProject(emarboxProject.getId(), emarboxProject.getSocialUserId());
            }
        }
    }

    private void saveProjectCode(EmarboxProject emarboxProject) {
        String projectCode = AliasUtil.encode(emarboxProject.getId());
        emarboxProject.setProjectCode(projectCode);
        EmarboxProject updateProjectCode = new EmarboxProject();
        updateProjectCode.setProjectCode(emarboxProject.getProjectCode());
        Example updateExample = new Example(EmarboxProject.class);
        updateExample.createCriteria().andEqualTo("id", emarboxProject.getId()).andEqualTo("userId", emarboxProject.getUserId()).andEqualTo("userType", emarboxProject.getUserType()).andEqualTo("projectName", emarboxProject.getProjectName());
        mapperAuthProvider.getEmarboxProjectMapper().updateByExampleSelective(updateProjectCode, updateExample);
    }

    public boolean projectUserRelation(EmarboxProject emarboxProject) {
        String userType = emarboxProject.getUserType();
        Long userId = emarboxProject.getUserId();
        Example delUserProjectExample = new Example(EmarboxUserProject.class);
        delUserProjectExample.createCriteria().andEqualTo("userId", emarboxProject.getUserId()).andEqualTo("projectId", emarboxProject.getId()).andEqualTo("moduleId", 3);
        mapperAuthProvider.getEmarboxUserProjectMapper().deleteByExample(delUserProjectExample);
        EmarboxUserProject emarboxUserProject = new EmarboxUserProject();
        emarboxUserProject.setCreateTime(new Date());
        emarboxUserProject.setCreateUser("advertiser_manage");
        emarboxUserProject.setModuleId(3L);
        emarboxUserProject.setProjectId(emarboxProject.getId());
        emarboxUserProject.setUserType(userType);
        emarboxUserProject.setUserId(userId);
        boolean addUserProjectIsOK = mapperAuthProvider.getEmarboxUserProjectMapper().insert(emarboxUserProject) > 0;

        Example delUserProjectModuleExample = new Example(EmarboxProjectModule.class);
        delUserProjectModuleExample.createCriteria().andEqualTo("projectId", emarboxProject.getId()).andEqualTo("moduleId", 3);
        mapperAuthProvider.getEmarboxProjectModuleMapper().deleteByExample(delUserProjectModuleExample);
        EmarboxProjectModule emarboxProjectModule = new EmarboxProjectModule();
        emarboxProjectModule.setCreateTime(new Date());
        emarboxProjectModule.setCreateUser("advertiser_manage");
        emarboxProjectModule.setProjectId(emarboxProject.getId());
        emarboxProjectModule.setModuleId(3L);
        boolean addProjectModuleIsOk = mapperAuthProvider.getEmarboxProjectModuleMapper().insert(emarboxProjectModule) > 0;
        return addUserProjectIsOK && addProjectModuleIsOk;
    }
}
