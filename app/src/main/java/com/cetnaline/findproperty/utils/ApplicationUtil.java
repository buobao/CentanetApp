package com.cetnaline.findproperty.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.cetnaline.findproperty.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    /**
     * dp转px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 判断软键盘是否弹出
     * @param context
     * @return
     */
    public static boolean isSoftShowing(Activity context) {
        //获取当前屏幕内容的高度
        int screenHeight = context.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight*2/3 > rect.bottom;
    }

    /**
     * 显示软键盘
     * @param context
     * @param view
     */
    public static void showSoftInput(Context context,View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏软键盘
     * @param context
     * @param view
     */
    public static void hideSoftInput(Context context,View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    final static char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * md5加密 用于部分数据请求及图片压缩
     * @param strings
     * @return
     */
    public static String md5Encode(String... strings) {
        final StringBuilder input = new StringBuilder();
        for (String s : strings) {
            input.append(s);
        }
        Log.d("md5 : %s", input.toString());
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.toString().getBytes());
            byte[] bytes = messageDigest.digest();
            int len = bytes.length;
            char[] resultCharArray = new char[len * 2];
            int index = 0;
            for (byte b : bytes) {
                resultCharArray[index++] = HEX_DIGITS[b >>> 4 & 0xf];
                resultCharArray[index++] = HEX_DIGITS[b & 0xf];
            }
            return new String(resultCharArray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
