package com.cetnaline.findproperty;

import android.app.Application;
import android.content.Context;

import com.cetnaline.findproperty.model.cache.CacheHolder;
import com.cetnaline.findproperty.model.database.DatabaseHelper;
import com.cetnaline.findproperty.utils.ApplicationUtil;
import com.cetnaline.findproperty.utils.ThirdShareUtil;

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
        }
    }



    public static Context getContext() {
        return mContext;
    }
}
