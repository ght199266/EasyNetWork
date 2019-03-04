EasyNetWork是一款网络变换监听框架，基于EventBus设计思路,使用简单

## How to use

##### 0、初始化
```java
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getDefault().init(this);
    }
}
```
##### 1、注册广播监听

```java
 NetWorkManager.getDefault().registerNetWorkObserver(this);
```
##### 2、自定义接收方法

```java
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
```
##### 3、注销 

```java
     NetWorkManager.getDefault().unRegisterObserver(this);
     //NetWorkManager.getDefault().unAllRegisterObserver(); 退出程序调用
```
