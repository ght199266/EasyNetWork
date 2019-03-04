package com.lly.network;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lly.networklib.NetType;
import com.lly.networklib.annotation.NetWork;
import com.lly.networklib.manager.NetWorkManager;


public class MainActivity extends AppCompatActivity {

    TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_info = findViewById(R.id.tv_info);
        NetWorkManager.getDefault().registerNetWorkObserver(this);
    }

    /**
     * All("所有"),
     * WIFI("Wifi"),
     * MOBILE("手机"),
     * NONE("无网络");
     * @param netType
     */
    @NetWork(netType = NetType.All)
    public void netWorkEvent(NetType netType) {
        tv_info.setText(String.format("网络类型：=%s", netType));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        NetWorkManager.getDefault().unRegisterObserver(this);
        NetWorkManager.getDefault().unAllRegisterObserver();
    }


}
