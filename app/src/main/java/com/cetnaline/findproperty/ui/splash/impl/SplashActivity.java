package com.cetnaline.findproperty.ui.splash.impl;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.cetnaline.findproperty.BuildConfig;
import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.model.network.NetWorkManager;
import com.cetnaline.findproperty.ui.splash.SplashPresenter;
import com.cetnaline.findproperty.ui.splash.SplashView;

import java.util.List;

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
            requestBaseData();
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
        NetWorkManager.getInstance().resetHost(host);
        //获取基础数据
        requestBaseData();
    }

    private void requestBaseData() {
        requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(List<String> grantedPermission,List<String> deniedPermission) {
                showMessage(getResources().getString(R.string.no_storage_permission_msg));
            }
        });
    }
}
























