package com.cetnaline.findproperty.ui.splash;


import android.content.Context;

import com.cetnaline.findproperty.base.IPresenter;

public interface SplashPresenter extends IPresenter<SplashView> {
    //获取app服务器地址
    void getAppHost();
    //获取app基础数据
    void getAppBaseData();
    //检查当前数据库是否更新
    void checkDatabaseVersion(Context context);
}
