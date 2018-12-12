package com.cetnaline.findproperty.ui.login.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.ui.login.LoginPresenter;
import com.cetnaline.findproperty.ui.login.LoginView;
import com.cetnaline.findproperty.utils.ApplicationUtil;
import com.cetnaline.findproperty.utils.statusbar.StatusBarUtil;
import com.cetnaline.findproperty.widgets.AnimationLayout;
import com.cetnaline.findproperty.widgets.ClearableEditView;
import com.cetnaline.findproperty.widgets.CodeInputView;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
    @BindView(R.id.phone_number_edt)
    protected ClearableEditView phoneEditView;
    @BindView(R.id.code_layout)
    protected AnimationLayout codeLayout;
    @BindView(R.id.back)
    protected ImageView codeBack;
    @BindView(R.id.msg_table)
    protected TextView msgTable;
    @BindView(R.id.msg_code)
    protected CodeInputView msgCode;

//    private FullScreenDialog codeDialog;

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
                msgCode.cleartext();
                msgTable.setText("验证码发送至 +86 " + s.toString());
                codeLayout.setVisibility(View.VISIBLE);
            }
        });
        mPresenter.onViewClick(codeBack, v -> codeLayout.setVisibility(View.GONE));
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenterImpl();
    }
}

























