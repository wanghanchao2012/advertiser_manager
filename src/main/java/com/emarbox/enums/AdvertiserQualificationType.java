package com.emarbox.enums;

public enum AdvertiserQualificationType {
    BUSINESS_LICENCE(5001001,"营业执照"),
    IPC(5001002,"ICP备案证书"),
    ID_POSITIVE(5001003,"法人代表身份证（正面）"),
    ID_NEGATIVE(50010031,"法人代表身份证（反面）");

    private Integer index;
    private String desc;

    AdvertiserQualificationType(Integer index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
