package com.lly.networklib.manager;

import android.app.Application;
import android.content.IntentFilter;

import com.lly.networklib.NetType;
import com.lly.networklib.annotation.NetWork;
import com.lly.networklib.broadcast.NetworkBroadcast;
import com.lly.networklib.listener.NetworkListenerCallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * NewWorkManager[v 1.0.0]
 * classes:network.lly.com.networklib.manager.NewWorkManager
 *
 * @author lileiyi
 * @date 2019/2/28
 * @time 11:25
 * @description
 */
public class NetWorkManager {

    private static volatile NetWorkManager netWorkManager;

    private Map<Object, List<MethodManager>> mMethodManager;

    private NetworkBroadcast mNetWorBroadCast;

    private Application mApplication;

    private NetWorkListener mNetWorkListener;


    private NetWorkManager() {
        mMethodManager = new HashMap<>();
        mNetWorkListener = new NetWorkListener();
    }

    public static NetWorkManager getDefault() {
        if (netWorkManager == null) {
            synchronized (NetWorkManager.class) {
                if (netWorkManager == null) {
                    netWorkManager = new NetWorkManager();
                }
            }
        }
        return netWorkManager;
    }

    /**
     * 初始化网络监听管理器
     */
    public void init(Application application) {
        this.mApplication = application;
        registerBroadcast(mNetWorkListener);
    }


    /**
     * 注册网络
     * @param object
     */
    public void registerNetWorkObserver(Object object) {
        if (mMethodManager == null) {
            mMethodManager = new HashMap<>();
        }
        List<MethodManager> managerList = mMethodManager.get(object);
        if (managerList == null) {
            managerList = findAnnotationMethod(object);
        }
        if (managerList.isEmpty()) {
            throw new RuntimeException("必须有一个注解NetWork方法");
        }
        mMethodManager.put(object, managerList);
    }


    /**
     * 在当前查找有NetWork注解的方法
     *
     * @param object Activity or Fragment
     * @return List<MethodManager>
     */
    private List<MethodManager> findAnnotationMethod(Object object) {
        List<MethodManager> methodsList = new ArrayList<>();
        Class Clazz = object.getClass();
        Method[] methods = Clazz.getDeclaredMethods();//获取本类所有的Public方法
        for (Method method : methods) {
            NetWork netWork = method.getAnnotation(NetWork.class);
            if (netWork != null) {
                Class<?> Clz = checkMethodNorm(method);
                MethodManager manager = new MethodManager(Clz, netWork.netType(), method);
                methodsList.add(manager);
            }
        }
        return methodsList;
    }


    /**
     * 检查方法是否规范
     */
    private Class<?> checkMethodNorm(Method method) {
        Type Type = method.getGenericReturnType();
        if (!"void".equals(Type.toString())) {
            throw new RuntimeException("the method return parameter must be void type");
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1) {
            throw new RuntimeException("Parameters can be only one");
        }
        return parameterTypes[0];
    }


    public void unRegisterObserver(Object object) {
        if (!mMethodManager.isEmpty()) {
            mMethodManager.remove(object);
        }
    }

    /**
     * 注册所有
     */
    public void unAllRegisterObserver() {
        if (!mMethodManager.isEmpty()) {
            mMethodManager.clear();
            mMethodManager = null;
        }
        unRegisterBroadcast();
    }

    /**
     * 网络监听回调
     */
    private class NetWorkListener implements NetworkListenerCallback {

        @Override
        public void onConnectSuccess(NetType netType) {
            post(netType);
        }

        @Override
        public void disConnect() {
            NetType netType = NetType.NONE;
            post(netType);
        }
    }

    private void post(NetType netType) {
        if (mMethodManager != null && mMethodManager.size() > 0) {
            Set<Object> object = mMethodManager.keySet();
            for (Object obj : object) {
                List<MethodManager> managerList = mMethodManager.get(obj);
                if (managerList != null && !managerList.isEmpty()) {
                    for (MethodManager manager : managerList) {
                        if (manager.getType().isAssignableFrom(netType.getClass())) {
                            invoke(manager, obj, netType);
                        }
                    }
                }
            }
        }
    }


    /**
     * 反射执行方法
     *
     * @param manager
     * @param obj
     * @param netType
     */
    private void invoke(MethodManager manager, Object obj, NetType netType) {
        try {
            manager.getMethod().invoke(obj, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 注册广播，监听网络变化
     *
     * @param listenerCallback
     */
    private void registerBroadcast(NetworkListenerCallback listenerCallback) {
        if (mNetWorBroadCast == null) {
            mNetWorBroadCast = new NetworkBroadcast(listenerCallback);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(NetworkBroadcast.ACTION);
            mApplication.registerReceiver(mNetWorBroadCast, intentFilter);
        }
    }


    /**
     * 解除注册
     */
    private void unRegisterBroadcast() {
        if (mNetWorBroadCast != null) {
            mApplication.unregisterReceiver(mNetWorBroadCast);
        }
    }

}
