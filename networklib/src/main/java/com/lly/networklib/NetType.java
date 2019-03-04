package com.lly.networklib;

/**
 * NetType[v 1.0.0]
 * classes:network.lly.com.networklib.NetType
 *
 * @author lileiyi
 * @date 2019/2/28
 * @time 11:10
 * @description
 */
public enum NetType {

    All("所有"),
    WIFI("Wifi"),
    MOBILE("手机"),
    NONE("无网络");

    private String desc;

    NetType(String des) {
        this.desc = des;
    }
}
