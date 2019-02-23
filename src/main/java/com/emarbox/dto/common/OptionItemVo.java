package com.emarbox.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionItemVo implements Serializable {
    private static final long serialVersionUID = -215250778461614813L;
    private Object text;
    private Object value;
    private Object realValue;

    public OptionItemVo preAppendValue() {
        text = "(" + value + ")".concat(this.text.toString());
        return this;
    }
}
