package com.cetnaline.findproperty;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.cetnaline.findproperty.model.cache.CacheHolder;
import com.cetnaline.findproperty.model.database.DatabaseHelper;
import com.cetnaline.findproperty.utils.ApplicationUtil;
import com.cetnaline.findproperty.utils.RongUtil;
import com.cetnaline.findproperty.utils.ThirdShareUtil;

import io.rong.imkit.RongIM;

public class FindPropertyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        if (BuildConfig.APPLICATION_ID.equals(ApplicationUtil.getCurProcessName(this))) {
            //init app cache
            CacheHolder.init(this);
            //init database
            DatabaseHelper.init(this);
            ThirdShareUtil.init();
            RongIM.init(this, BuildConfig.RONG_CLOUD_KEY);
            RongUtil.connectServer();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {
        return mContext;
    }
}
