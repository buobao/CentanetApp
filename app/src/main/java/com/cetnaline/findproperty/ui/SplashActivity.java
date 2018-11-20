package com.cetnaline.findproperty.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.cetnaline.findproperty.BuildConfig;
import com.cetnaline.findproperty.network.services.imp.ApiRequestImp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class SplashActivity extends AppCompatActivity {
    List<Disposable> disposableList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //全屏

        if (BuildConfig.DEBUG) {
            //基础数据请求
        } else {

        }
        disposableList.add(ApiRequestImp.getAppServiceAddressRequest().subscribe(stringBaseResponseBean -> {
            Toast.makeText(this,stringBaseResponseBean.getResult(),Toast.LENGTH_SHORT).show();
        }));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposableList.size() > 0) {
            for (Disposable disposable : disposableList) {
                if (!disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        }
    }
}
