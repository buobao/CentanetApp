package com.cetnaline.findproperty.ui.login;

import com.cetnaline.findproperty.base.IPresenter;

import java.util.Map;

public interface LoginPresenter extends IPresenter<LoginView> {
    //请求验证码
    void requestCode(String phone);
    //取消验证码计时
    void canceltimer();
    //用户登录
    void userLogin(Map<String, String> params);
}
