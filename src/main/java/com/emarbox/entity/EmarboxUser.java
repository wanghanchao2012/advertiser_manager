package com.emarbox.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

@Builder
@Data
@Table(name = "emarbox_user")
@AllArgsConstructor
@NoArgsConstructor
public class EmarboxUser {
    //用户ID
    private Long userId;

    //用户类型: 管理员 admin ,  主帐号 user, 子帐号 sub_user
    private String userType;

    //父级用户ID
    private Long parentUserId;

    //用户登录名
    private String loginName;

    //用户密码
    private String password;

    //用户显示名
    private String displayName;

    //是否已锁定
    private Long locked;

    //是否已删除
    private Long deleted;

    //最后登录时间
    private Date lastLoginTime;

    //最后登录IP
    private String lastLoginIp;

    //登录次数
    private Long loginTimes;

    //创建时间
    private Date createTime;

    //创建者
    private String createUser;

    //更新时间
    private Date updateTime;

    //更新者
    private String updateUser;

    //是否是超管
    private Integer isRoot = 0;


    private Date startDate;


    private Date endDate;

    //是否是代理商用户 1 是 0 不是
    private Long isAgent = 0L;

    //是否是SAAS用户 1是 0 不是
    private Long isSaas = 0L;

    //客户行业类型
    private Long industryType;

    //结算类型 1 保ROI 0 不保ROI
    private Integer settlementType;

    //0 测试用户 1 正式用户
    private Long saasStatus = 0L;

}
