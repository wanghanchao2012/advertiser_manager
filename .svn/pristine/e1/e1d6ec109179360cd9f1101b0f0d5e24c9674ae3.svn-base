package com.emarbox.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdvertiserOptionItemVo {
    private Object text;
    private Object value;
    private Object realValue;
    private String licenseCode;

    public AdvertiserOptionItemVo preAppendValue() {
        text = "(" + value + ")".concat(this.text.toString());
        return this;
    }
}
