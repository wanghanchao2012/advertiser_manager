package com.emarbox.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDisputeResult {

    public Integer code;

    public String message;

    public static ResponseDisputeResult forFail() {
        return new ResponseDisputeResult(-1, "exception");
    }

    @JsonIgnore
    public boolean isFail() {
        return Objects.nonNull(code) && code.intValue() != 0;
    }

    @JsonIgnore
    public boolean isOk() {
        return Objects.nonNull(code) && code.intValue() == 0;
    }
}
