package com.emarbox.entity;

import java.util.Date;
import java.util.EnumSet;
import java.util.stream.Collectors;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.emarbox.enums.QualificationType;
import com.emarbox.enums.StatusType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pig_agent_info")
@Data
public class AgentInfo {

	@Id
    private Long id;

    //代理商名称
    private String agentName;

    //公司名称
    private String corporation;

    //开户时间
    private Date openAccountTime;

    //邮件
    private String email;

    //资质类型
    private String qualificationType;

    //资质图片
    private String qualificationImg;

    //资质编号
    private String qualificationCode;

    private String qualificationSignature;

    //联系人
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
    
    private Integer giftType;
    
    private Double giftRatio;

    public String getStatusText() {
        if (StringUtils.isBlank(status)) {
            status = StatusType.pending.name();
        }
        return StatusType.valueOf(status).getDesc();
    }

    @Transient
    private String statusText;

    public String getQualificationTypeText() {
        return EnumSet.allOf(QualificationType.class).stream()
                .filter(e->e.getIndex().equals(Integer.parseInt(qualificationType)))
                .map(e->e.getDesc())
                .collect(Collectors.joining(","));
    }


    @Transient
    private String qualificationTypeText;
    @Transient
    private Double cashBalance;
    @Transient
    private Double giftBalance;
    @Transient
    private Long currentUserId;
}
