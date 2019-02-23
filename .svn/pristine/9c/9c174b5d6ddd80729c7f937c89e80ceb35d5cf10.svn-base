package com.emarbox.enums;

public enum  ErrorInfos {
    MISSING_DATA(-1000,"数据不完整，请联系管理员"),
    EXIST_BUILD_ADVETISER_RELATION(-1001,"已经存在绑定关系，请解绑后再绑定"),
    GDT_NOT_ADVERTISEER(-1002,"广点通不存在该广告主ID，请确认后重新绑定"),
    TOU_TIAO_NOT_ADVERTISEER(-1003,"头条不存在该广告主ID，请确认后重新绑定"),
    MISSING_REQUIRED_PARAM(-1004,"缺少必要参数，请确认");
    private Integer code;
    private String desc;


    ErrorInfos(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
