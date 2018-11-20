package com.cetnaline.findproperty.ui.splash.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.cetnaline.findproperty.BuildConfig;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.network.NetWorkContents;
import com.cetnaline.findproperty.ui.splash.SplashPresenter;
import com.cetnaline.findproperty.ui.splash.SplashView;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashView {

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //全屏

        if (BuildConfig.DEBUG) {
            //基础数据请求

        } else {
            mPresenter.getAppHost();
        }

    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenterImpl();
    }


    @Override
    public void updateAppHost(String host) {
        showMessage(host);
        NetWorkContents.CENTANET_BASE_HOST = host;
        //获取基础数据

    }
}
