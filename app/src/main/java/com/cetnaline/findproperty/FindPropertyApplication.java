package com.cetnaline.findproperty;

import android.app.Application;
import android.content.Context;

import com.cetnaline.findproperty.network.NetWorkManager;
import com.cetnaline.findproperty.utils.ApplicationUtil;

public class FindPropertyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        if (BuildConfig.APPLICATION_ID.equals(ApplicationUtil.getCurProcessName(this))) {
            //init something

        }
    }

    public static Context getContext() {
        return mContext;
    }
}
