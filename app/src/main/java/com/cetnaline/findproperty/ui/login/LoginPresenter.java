package com.cetnaline.findproperty.ui.login;

import com.cetnaline.findproperty.base.IPresenter;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.Map;

public interface LoginPresenter extends IPresenter<LoginView> {
    //请求验证码
    void requestCode(String phone);
    //取消验证码计时
    void canceltimer();
    //用户登录
    void userLogin(Map<String, String> params, String headImg);
    //获取qq用户信息
    void requestQQUserInfo(Map<String, String> params);
    //获取微博用户信息
    void requestSinaUserInfo(Oauth2AccessToken accessToken);
}
