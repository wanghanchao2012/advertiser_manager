package com.emarbox.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name="pig_project_certificate")
public class ProjectCertificate {

    private Long id;

    private Long advertiserId;
    //资质ID:app_constantwhere parent_id=5001
    private Long certificateId;
    //资质地址
    private String filePath;
    //文件宽
    private Long fileWidth;
    //文件高
    private Long fileHeight;
    //文件大小
    private Double fileSize;

    private String signature;

    private Date createTime;

    private Date updateTime;

    private String updateUser;

    private String createUser;

}
