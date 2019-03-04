package com.lly.networklib.netutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import com.lly.networklib.NetType;


public class NetWorkUtils {

    /**
     * 检查网络是否连接
     */
    public static boolean checkNetWorkConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connMgr.getActiveNetwork();
            if (network != null) {
                return connMgr.getNetworkInfo(network).isConnected();
            }
            return false;
        } else {
            //获取所有网络连接的信息
            if (Build.VERSION.SDK_INT >= 21) {
                Network[] networks = connMgr.getAllNetworks();
                for (Network network : networks) {
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
                    if (networkInfo != null && networkInfo.isConnected()) {
                        return true;
                    }
                }
            } else {
                NetworkInfo[] networkInfo = connMgr.getAllNetworkInfo();
                for (NetworkInfo info : networkInfo) {
                    if (info != null && info.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取网络类型
     */
    public static NetType getNetworkType(Context context) {
        NetType netType = NetType.NONE;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo info = connMgr.getActiveNetworkInfo();
            if (info != null) {
                switch (info.getType()) {
                    case ConnectivityManager.TYPE_WIFI:
                        netType = NetType.WIFI;
                        break;
                    default:
                        netType = NetType.MOBILE;
                        break;
                }
            }
        }
        return netType;
    }


}
