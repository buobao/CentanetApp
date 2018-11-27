package com.cetnaline.findproperty.ui.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;

import com.cetnaline.findproperty.bus.RxBus;
import com.cetnaline.findproperty.bus.events.NormalEvent;
import com.cetnaline.findproperty.model.cache.CacheHolder;
import com.cetnaline.findproperty.utils.ApplicationUtil;

public class NetworkStateService extends Service {

    private static final String tag="tag";
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        private String getConnectionType(int type) {
            String connType = "";
            if (type == ConnectivityManager.TYPE_MOBILE) {
                connType = "3G/4G";
            } else if (type == ConnectivityManager.TYPE_WIFI) {
                connType = "WIFI网络";
            }
            return connType;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 监听wifi的打开与关闭，与wifi的连接无关
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        break;
                }
            }
            // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                //获取联网状态的NetworkInfo对象
                if (ApplicationUtil.checkNetwork(context)) {
                    if (!CacheHolder.getInstance().isNetworkStatus()) {
                        CacheHolder.getInstance().setNetworkStatus(true);
                        RxBus.getInstance().post(new NormalEvent(NormalEvent.NETWORK_CONNECTED));
                    }
                } else {
                    if(CacheHolder.getInstance().isNetworkStatus()) {
                        CacheHolder.getInstance().setNetworkStatus(false);
                        RxBus.getInstance().post(new NormalEvent(NormalEvent.NETWORK_DISCONNECTED));
                    }
                }
//                NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
//                if (info != null) {
//                    //如果当前的网络连接成功并且网络连接可用
//                    if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
//                        if (info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == ConnectivityManager.TYPE_MOBILE) {
////                        Log.i("TAG", getConnectionType(info.getType()) + "连上");
//                            CacheHolder.getInstance().setNetworkStatus(true);
//                            RxBus.getInstance().post(new NormalEvent(NormalEvent.NETWORK_CONNECTED));
//                        }
//                    } else {
////                    Log.i("TAG", getConnectionType(info.getType()) + "断开");
//                        CacheHolder.getInstance().setNetworkStatus(false);
//                        RxBus.getInstance().post(new NormalEvent(NormalEvent.NETWORK_DISCONNECTED));
//                    }
//                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
