package com.emarbox.mapper.auth;

import com.emarbox.common.BaseMapper;
import com.emarbox.dto.ProjectInfoQuery;
import com.emarbox.entity.EmarboxProject;

import java.util.List;

public interface EmarboxProjectMapper extends BaseMapper<EmarboxProject> {

    public List<EmarboxProject> getListByPage(ProjectInfoQuery query);

    public Integer getCountByPage(ProjectInfoQuery query);

    public EmarboxProject getProjectInfo(EmarboxProject query);
}