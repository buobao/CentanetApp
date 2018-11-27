package com.cetnaline.findproperty.ui.splash.impl;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.cetnaline.findproperty.BuildConfig;
import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.model.cache.CacheHolder;
import com.cetnaline.findproperty.model.network.NetWorkManager;
import com.cetnaline.findproperty.ui.MainActivity;
import com.cetnaline.findproperty.ui.guild.impl.GuildActivity;
import com.cetnaline.findproperty.ui.service.NetworkStateService;
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
        Intent i=new Intent(this,NetworkStateService.class);
        startService(i);
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

    @Override
    public void goNext() {
        if (CacheHolder.getInstance().getGuildVersion() < BuildConfig.GUILD_FLAG) {
            CacheHolder.getInstance().setGuildVersion(BuildConfig.GUILD_FLAG);
            //启动引导页
            Intent intent = new Intent(this, GuildActivity.class);
            startActivity(intent);

        } else {
            //启动首页
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void connectNetworkHandler() {
        showMessage("网络已连接");
    }

    @Override
    public void disConnectNetworkHandler() {
        showMessage("网络已断开");
    }

    private void requestBaseData() {
        requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                mPresenter.checkDatabaseVersion(SplashActivity.this);
            }

            @Override
            public void onDenied(List<String> grantedPermission,List<String> deniedPermission) {
                showMessage(getResources().getString(R.string.no_storage_permission_msg));
                finish();
            }
        });
    }
}
























