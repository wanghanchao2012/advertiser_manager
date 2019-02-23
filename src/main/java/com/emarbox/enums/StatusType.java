package com.emarbox.enums;

/**
 * 计划状态
 * 待审核：pending，审核通过：normal，拒绝：reject
 */
public enum StatusType {

    pending("待审核"),
    normal("已通过"),
    reject("审核不通过");
    private String desc;

    StatusType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
