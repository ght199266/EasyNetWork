package com.lly.network;

import android.app.Application;

import com.lly.networklib.manager.NetWorkManager;


/**
 * MainApplication[v 1.0.0]
 * classes:com.lly.network.MainApplication
 *
 * @author lileiyi
 * @date 2019/2/28
 * @time 16:30
 * @description
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getDefault().init(this);
    }
}
