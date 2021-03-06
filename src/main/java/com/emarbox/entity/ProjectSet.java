package com.emarbox.entity;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "project_set")
public class ProjectSet {

    private Long id;

    //项目ID
    private Long projectId;

    //0 先审后投 1先投后审
    private Long auditGrade;

    //0 关闭 1开启
    private Long autoApi;

    //0 关闭 1开启
    private Long dynamicCreative;

    //创建人
    private String createUser;

    //创建时间
    private Date createTime;

    //修改人
    private String updateUser;

    //修改时间
    private Date updateTime;

    //0 关闭 1开启
    private Long pointApi;

    //0 关闭 1开启
    private Long socialApi;

    //广点通等级权限
    private Long socialGrade = 0L;

    //广点通的账户ID
    private Long socialUserId;

    //广点通社交余额
    private Double socialBalance;

    //广点通社交返点余额
    private Double socialPresentBalance;

    //自动选择行业类别
    private Long autoCheckTrade = 0L;

    //最后一次后台设置渠道的活动ID
    private Long lastChangeCampaign;

    //项目级别频次控制-0不限,1设置
    private Integer frequencyControl;

    //0: A类，收取毛利的光公主 1：B类，不收取毛利的广告主
    private Long advertiserType;

    //部码情况-0关闭,1-开启
    private Integer codeDeploy = 0;

    //是否支持移动动态创意(1开启,0关闭)
    private Integer dynamicMobileCreative = 0;


}
