package com.emarbox.dto;

import com.emarbox.dto.common.RequestParamVo;
import com.emarbox.enums.StatusType;
import lombok.Data;

@Data
public class AdvertiserInfoQuery extends RequestParamVo {
    private String advertiserName;
    private String agentName;
    private String status;
}
