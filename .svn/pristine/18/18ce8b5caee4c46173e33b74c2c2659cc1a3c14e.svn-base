package com.emarbox.entity;

import com.emarbox.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Builder
@Table(name = "emarbox_project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmarboxProject {

    @Id
    @Column(name="project_id")
    //项目ID
    private Long id;

    //用户ID
    private Long userId;

    //用户类型: 管理员 admin ,  主帐号 user, 子帐号 sub_user
    private String userType;

    //项目名称
    private String projectName;

    //项目网址
    private String projectUrl;

    //项目备注
    private String projectMemo;

    //项目联系人
    private String linkman;

    //联系人电话
    private String telphone;

    //联系人手机号码
    private String mobile;

    //联系人邮箱
    private String email;

    //是否已删除
    private Long deleted;

    //创建时间
    private Date createTime;

    //创建者
    private String createUser;

    //更新时间
    private Date updateTime;

    //更新者
    private String updateUser;

    //项目编码
    private String projectCode;

    //项目分类ID
    private Long projectCatId;

    //网站类型代码
    private String siteTypeCode;

    //是否是代理商项目：1 是 0 不是
    private Long isAgent = 0L;

    //地域编码
    private String areaCode;

    //品牌名称
    private String brandName;

    //关联QQ
    private String relatedQq;

    private String supervisor;
    private String sales;

    private Integer orgId;

    /**
     * 经过讨论 升级的广告主管理任然使用emarbox_project表所以需要增加本次升级需要的列 以“pig”开头的都是2019年升级的新字段
     */
    @Column(name = "pig_advertiser_id")
    private Long advertiserId;

    //归属销售
    @Column(name = "pig_salesman_id")
    private Long salesmanId;

    //归属运营
    @Column(name = "pig_department_id")
    private Long departmentId;

    //营业执照号
    @Column(name = "pig_license_code")
    private String licenseCode;

    //投放平台
    @Column(name = "pig_platform_id")
    private Integer platformId;

    //所属行业
    @Column(name = "pig_category_type")
    private Long categoryType;
    //所属行业二级
    @Column(name = "pig_sub_category_type")
    private Long subCategoryType;
    /**
     * 待审核：pending，审核通过：normal，拒绝：reject
     */
    @Column(name = "pig_status")
    private String status;

    public String getStatusText() {
        if (StringUtils.isBlank(status)) {
            status = StatusType.pending.name();
        }
        return StatusType.valueOf(status).getDesc();
    }


    @Transient
    private String subCategoryName;
    @Transient
    private String categoryName;
    @Transient
    private String salesmanName;
    @Transient
    private String departmentName;
    @Transient
    private String platformName;
    @Transient
    private String statusText;
    @Transient
    private String advertiserName;
    @Transient
    private Long socialUserId;
    @Transient
    //现金余额
    private Double cashBalance;
    @Transient
    //虚拟余额
    private Double giftBalance;
}
