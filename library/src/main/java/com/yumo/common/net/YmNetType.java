package com.yumo.common.net;

public enum YmNetType {
    NETWORK_WIFI("WiFi"),
    NETWORK_5G("5G"),
    NETWORK_4G("4G"),
    NETWORK_3G("3G"),
    NETWORK_2G("2G"),
    NETWORK_UNKNOWN("Unknown"),
    NETWORK_NO("No Network");

    private String desc;
    YmNetType(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }

    public boolean isMoble(){
        return this.equals(NETWORK_2G) || this.equals(NETWORK_3G) || this.equals(NETWORK_4G) || this.equals(NETWORK_5G);
    }
}
