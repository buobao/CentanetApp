package com.cetnaline.findproperty.ui.splash;

import com.cetnaline.findproperty.base.BaseView;

public interface SplashView extends BaseView {
    //更新app服务器地址
    void updateAppHost(String host);
    //进入下级页面
    void goNext();
}
