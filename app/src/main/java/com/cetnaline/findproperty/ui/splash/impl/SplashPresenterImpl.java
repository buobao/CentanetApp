package com.cetnaline.findproperty.ui.splash.impl;

import com.cetnaline.findproperty.base.BasePresenter;
import com.cetnaline.findproperty.network.services.imp.ApiRequestImp;
import com.cetnaline.findproperty.ui.splash.SplashPresenter;
import com.cetnaline.findproperty.ui.splash.SplashView;

public class SplashPresenterImpl extends BasePresenter<SplashView> implements SplashPresenter {
    @Override
    public void getAppHost() {
        addDisposable(ApiRequestImp.getAppServiceAddressRequest().subscribe(stringBaseResponseBean -> {
            iView.updateAppHost(stringBaseResponseBean.getResult());
        }, throwable -> {
            throwable.printStackTrace();
            iView.showMessage(throwable.getMessage());
        }));
    }

    @Override
    public void getAppBaseData() {

    }
}
