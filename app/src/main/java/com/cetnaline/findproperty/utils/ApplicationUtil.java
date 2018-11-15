package com.cetnaline.findproperty.utils;

import android.app.ActivityManager;
import android.content.Context;

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
}
