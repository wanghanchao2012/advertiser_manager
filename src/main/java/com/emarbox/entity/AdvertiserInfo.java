package com.emarbox.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.emarbox.enums.StatusType;

import lombok.Data;

@Table(name = "pig_advertiser_info")
@Data
public class AdvertiserInfo {

    private Long id;

    //广告主名称
    private String advertiserName;

    //审核信息
    private String auditMessage;

    //代理商
    private Long agentId;

    //统一社会信用代码
    private String creditCode;

    //公司网站
    private String companyWebsite;

    //所属行业
    private Long categoryType;

    private String linkMan;

    private String phoneNumber;
    /**
     * 待审核：pending，审核通过：normal，拒绝：reject
     */
    private String status;
    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;
    
    //返点方式： 0:无返点,1:即冲即返,2:消耗返点
    private Integer giftType;
    
    private Double giftRatio;
    
    @Transient
    private String agentName;
    @Transient
    private Double cashBalance;
    @Transient
    private Double giftBalance;
    @Transient
    private Long currentUserId;
    @Transient
    private String statusText;
    @Transient
    private List<CertificateSource> certificateSource;

    public String getStatusText() {
        if (StringUtils.isBlank(status)) {
            status = StatusType.pending.name();
        }
        return StatusType.valueOf(status).getDesc();
    }

}
