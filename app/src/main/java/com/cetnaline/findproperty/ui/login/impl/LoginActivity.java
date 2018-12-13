package com.cetnaline.findproperty.ui.login.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.bus.RxBus;
import com.cetnaline.findproperty.bus.events.NormalEvent;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.UserInfoBean;
import com.cetnaline.findproperty.ui.login.LoginPresenter;
import com.cetnaline.findproperty.ui.login.LoginView;
import com.cetnaline.findproperty.utils.ApplicationUtil;
import com.cetnaline.findproperty.utils.statusbar.StatusBarUtil;
import com.cetnaline.findproperty.widgets.AnimationLayout;
import com.cetnaline.findproperty.widgets.ClearableEditView;
import com.cetnaline.findproperty.widgets.CodeInputView;

import java.util.HashMap;
import java.util.Map;

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
    @BindView(R.id.tx_timer)
    protected TextView txTimer;
    @BindView(R.id.request_msg)
    protected TextView requestMsg;

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
                requestMsg.setVisibility(View.GONE);
                txTimer.setText("60s后重新发送");
                txTimer.setEnabled(false);
                mPresenter.requestCode(s.toString());
            }
        });
        mPresenter.onViewClick(codeBack, v -> codeLayout.setVisibility(View.GONE));
        mPresenter.onViewClick(txTimer,v->{
            txTimer.setEnabled(false);
            requestMsg.setVisibility(View.GONE);
            mPresenter.requestCode(phoneEditView.getText().toString());
        });

        msgCode.setOnInputFinished(code -> {
            Map<String, String> params = new HashMap<>();
            params.put("Phone", phoneEditView.getText().toString());
            params.put("VerificationCode", code);
            // TODO: 2018/12/13 邀请码登录在这里添加参数
//            params.put("YaoQingMa", yaoqingma);
            mPresenter.userLogin(params);
        });
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    public void onBackPressed() {
        if (codeLayout.getVisibility() == View.VISIBLE) {
            codeLayout.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setRequestCodeResult(BaseResponseBean<Integer> responseBean) {
        switch (responseBean.getResultNo()) {
            case BaseResponseBean.FAILE_CODE:
                if (TextUtils.isEmpty(responseBean.getMessage())) {
                    requestMsg.setText("发送验证码失败");
                } else {
                    requestMsg.setText(responseBean.getMessage());
                }
            case BaseResponseBean.REQUEST_ERROR_CODE:
                requestMsg.setText("服务器请求异常");
            case BaseResponseBean.REQUEST_OVERTIME_CODE:
                requestMsg.setText("网络请求超时");
            case BaseResponseBean.REQUEST_NOT_CONNECTION_CODE:
                requestMsg.setText("服务器无法连接，请检查网络");
                requestMsg.setVisibility(View.VISIBLE);
                txTimer.setText("重新发送");
                txTimer.setEnabled(true);
                mPresenter.canceltimer();
                break;
        }
    }

    @Override
    public void updateTimerMsg(long currentSecond) {
        if ((59 - currentSecond) > 0) {
            txTimer.setText((60-currentSecond) +"s后重新发送");
        } else {
            txTimer.setText("重新发送");
            txTimer.setEnabled(true);
            mPresenter.canceltimer();
        }
    }

    @Override
    public void loginfinish(BaseResponseBean<UserInfoBean> responseBean) {
        switch (responseBean.getResultNo()) {
            case BaseResponseBean.FAILE_CODE:
                showMessage("登录失败");
                break;
            case BaseResponseBean.REQUEST_ERROR_CODE:
                showMessage("服务器请求异常");
                break;
            case BaseResponseBean.REQUEST_OVERTIME_CODE:
                showMessage("网络请求超时");
                break;
            case BaseResponseBean.REQUEST_NOT_CONNECTION_CODE:
                showMessage("服务器无法连接，请检查网络");
                break;
            case BaseResponseBean.SUCCESS_CODE:
                showMessage("登录成功");
                RxBus.getInstance().post(new NormalEvent(NormalEvent.LOGIN_SUCCESS)); //发送登录成功事件
                finish();
                break;
        }
    }
}

























