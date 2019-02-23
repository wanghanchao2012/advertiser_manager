package com.emarbox.entity;

import lombok.Data;

import javax.persistence.Table;

@Table(name = "pig_platform_industry_info")
@Data
public class PlatformIndustryInfo {

    private Long id;

    private Long industryId;

    private String industryName;

    private Long parentIndustryId;

    private String description;

    private Long platformId;

}
