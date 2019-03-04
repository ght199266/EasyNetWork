package com.lly.networklib.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lly.networklib.NetType;
import com.lly.networklib.listener.NetworkListenerCallback;
import com.lly.networklib.netutils.NetWorkUtils;

/**
 * NetwrokBroadcast[v 1.0.0]
 * classes:network.lly.com.networklib.broadcast.NetwrokBroadcast
 *
 * @author lileiyi
 * @date 2019/2/28
 * @time 15:28
 * @description 监听网络变换
 */
public class NetworkBroadcast extends BroadcastReceiver {

    public static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    private NetworkListenerCallback mNetWorBroadCast;


    public NetworkBroadcast() {
    }

    public NetworkBroadcast(NetworkListenerCallback mNetWorBroadCast) {
        this.mNetWorBroadCast = mNetWorBroadCast;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (intent.getAction().equals(ACTION)) {
            if (NetWorkUtils.checkNetWorkConnected(context)) {
                NetType netType = NetWorkUtils.getNetworkType(context);
                if (mNetWorBroadCast != null) {
                    mNetWorBroadCast.onConnectSuccess(netType);
                }
            } else {
                if (mNetWorBroadCast != null) {
                    mNetWorBroadCast.disConnect();
                }
            }
        }
    }
}
