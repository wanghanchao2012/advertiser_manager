package com.emarbox.enums;

public enum BalanceOperationType {

    cash("充值"),
    refund("退款");
    private String desc;

    BalanceOperationType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
