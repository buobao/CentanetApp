package com.cetnaline.findproperty.ui.login.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.ui.login.LoginPresenter;
import com.cetnaline.findproperty.ui.login.LoginView;
import com.cetnaline.findproperty.utils.ApplicationUtil;
import com.cetnaline.findproperty.utils.statusbar.StatusBarUtil;
import com.cetnaline.findproperty.widgets.ClearableEditView;
import com.cetnaline.findproperty.widgets.FullScreenDialog;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
    @BindView(R.id.phone_number_edt)
    protected ClearableEditView phoneEditView;

    private FullScreenDialog codeDialog;

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
        phoneEditView.setOnTextChangedListener(s -> {
            if (s.length() >= 11) {
                codeDialog.show();
            }
        });

        codeDialog = new FullScreenDialog(this, R.layout.dialog_login_code, view -> {

        });
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenterImpl();
    }
}

























