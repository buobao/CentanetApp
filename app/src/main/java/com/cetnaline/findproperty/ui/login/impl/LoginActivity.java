package com.cetnaline.findproperty.ui.login.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.ui.login.LoginPresenter;
import com.cetnaline.findproperty.ui.login.LoginView;
import com.cetnaline.findproperty.utils.ApplicationUtil;
import com.cetnaline.findproperty.utils.statusbar.StatusBarUtil;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    @Override
    protected void beforeViewInit(Bundle savedInstanceState) {
        //设置状态栏主题
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        //设置状态栏背景色
        StatusBarUtil.setStatusBarColor(this, ApplicationUtil.getPageColorPrimary(this));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenterImpl();
    }
}
