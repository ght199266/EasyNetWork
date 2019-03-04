package com.lly.networklib.manager;

import com.lly.networklib.NetType;

import java.lang.reflect.Method;

/**
 * MethodManager[v 1.0.0]
 * classes:network.lly.com.networklib.manager.MethodManager
 *
 * @author lileiyi
 * @date 2019/2/28
 * @time 14:09
 * @description
 */
public class MethodManager {

    //参数类型
    private Class<?> type;
    private NetType netType;
    private Method Method;


    public MethodManager(Class<?> type, NetType netType, java.lang.reflect.Method method) {
        this.type = type;
        this.netType = netType;
        Method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public java.lang.reflect.Method getMethod() {
        return Method;
    }

    public void setMethod(java.lang.reflect.Method method) {
        Method = method;
    }
}
