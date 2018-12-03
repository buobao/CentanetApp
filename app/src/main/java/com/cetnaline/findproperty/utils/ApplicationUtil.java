package com.cetnaline.findproperty.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;

import com.cetnaline.findproperty.R;

public class ApplicationUtil {

    public static String curProcessName = null;

    /**
     * 获取当前进程名
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        if (curProcessName == null) {
            int pid = android.os.Process.myPid();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                    .getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    curProcessName = appProcess.processName;
                    return appProcess.processName;
                }
            }
        }
        return curProcessName;
    }

    /**
     * 获取当前网络状态
     *
     * @param context
     * @return
     */
    public static boolean checkNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifiState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        NetworkInfo.State mobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        return wifiState == NetworkInfo.State.CONNECTED || mobileState == NetworkInfo.State.CONNECTED;
    }


    /**
     * 获取当前activity默认背景颜色
     * @param context
     * @return
     */
    public static int getPageColorPrimary(Context context){
        TypedValue typedValue = new  TypedValue();
        context.getTheme().resolveAttribute(R.attr.controlBackground, typedValue, true);
        return typedValue.data;
    }

}
