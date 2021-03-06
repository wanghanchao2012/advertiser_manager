package com.emarbox.entity;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "emarbox_user_project")
public class EmarboxUserProject {
    private Long userId; // 用户ID
    private String userType;//用户类型
    private Long projectId; // 项目ID
    private Long moduleId; // 模块ID
    private Date createTime; // 创建日期
    private String createUser; // 创建人
    private Date updateTime; // 更新日期
    private String updateUser; // 更新人
}
