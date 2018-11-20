package com.cetnaline.findproperty;

import android.app.Application;

import com.cetnaline.findproperty.network.NetWorkManager;
import com.cetnaline.findproperty.utils.ApplicationUtil;

public class FindPropertyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.APPLICATION_ID.equals(ApplicationUtil.getCurProcessName(this))) {
            //init something

        }
    }
}
