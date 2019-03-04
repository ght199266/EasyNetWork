package com.lly.networklib.listener;

import com.lly.networklib.NetType;

/**
 * NetworkListenerCallback[v 1.0.0]
 * classes:network.lly.com.networklib.listener.NetworkListenerCallback
 *
 * @author lileiyi
 * @date 2019/2/28
 * @time 16:48
 * @description
 */
public interface NetworkListenerCallback {

    void onConnectSuccess(NetType netType);

    void disConnect();

}
