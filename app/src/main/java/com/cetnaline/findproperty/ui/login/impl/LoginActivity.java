package com.cetnaline.findproperty.ui.login.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.ui.login.LoginPresenter;
import com.cetnaline.findproperty.ui.login.LoginView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
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
