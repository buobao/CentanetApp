package com.cetnaline.findproperty.ui.login.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cetnaline.findproperty.BuildConfig;
import com.cetnaline.findproperty.R;
import com.cetnaline.findproperty.base.BaseActivity;
import com.cetnaline.findproperty.bus.events.NormalEvent;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.QQTokenBean;
import com.cetnaline.findproperty.ui.login.LoginPresenter;
import com.cetnaline.findproperty.ui.login.LoginView;
import com.cetnaline.findproperty.utils.ApplicationUtil;
import com.cetnaline.findproperty.utils.ThirdShareUtil;
import com.cetnaline.findproperty.utils.statusbar.StatusBarUtil;
import com.cetnaline.findproperty.widgets.AnimationLayout;
import com.cetnaline.findproperty.widgets.ClearableEditView;
import com.cetnaline.findproperty.widgets.CodeInputView;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.UiError;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
    @BindView(R.id.close)
    protected ImageView closeBtn;
    @BindView(R.id.exchange_type)
    protected TextView exchangeTypeBtn;
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

    @BindView(R.id.invite_code_edt_layout)
    protected LinearLayout inviteCodeEdtLayout;
    @BindView(R.id.invite_code_edt)
    protected ClearableEditView inviteCodeEdt;
    @BindView(R.id.next_btn)
    protected TextView nextBtn;

    @BindView(R.id.login_for_qq)
    protected ImageView loginForQQ;
    @BindView(R.id.login_for_wx)
    protected ImageView loginForWX;
    @BindView(R.id.login_for_wb)
    protected ImageView loginForWB;

//    private FullScreenDialog codeDialog;

    @Override
    protected void beforeViewInit(Bundle savedInstanceState) {
        //设置状态栏主题
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        //设置状态栏背景色
        StatusBarUtil.setStatusBarColor(this, ApplicationUtil.getPageColorPrimary(this));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mPresenter.onViewClick(closeBtn, v -> finish());
        mPresenter.onViewClick(exchangeTypeBtn, v->{
            if (inviteCodeEdtLayout.getVisibility() == View.VISIBLE) {
                inviteCodeEdtLayout.setVisibility(View.GONE);
                nextBtn.setVisibility(View.GONE);
            } else {
                inviteCodeEdtLayout.setVisibility(View.VISIBLE);
                nextBtn.setVisibility(View.VISIBLE);
            }
        });
        phoneEditView.setOnTextChangedListener(s -> {
            if (s.length() >= 11 && inviteCodeEdtLayout.getVisibility() != View.VISIBLE) {
                goNextView(s.toString());
            }
        });
        mPresenter.onViewClick(codeBack, v -> codeLayout.setVisibility(View.GONE));
        mPresenter.onViewClick(txTimer,v->{
            txTimer.setEnabled(false);
            requestMsg.setVisibility(View.GONE);
            mPresenter.requestCode(phoneEditView.getText().toString());
        });

        inviteCodeEdt.setOnTextChangedListener(s -> {
            if (!TextUtils.isEmpty(s)) {
                nextBtn.setEnabled(true);
            } else {
                nextBtn.setEnabled(false);
            }
        });

        mPresenter.onViewClick(nextBtn, v -> goNextView(phoneEditView.getText().toString()));

        msgCode.setOnInputFinished(code -> {
            Map<String, String> params = new HashMap<>();
            params.put("Phone", phoneEditView.getText().toString());
            params.put("VerificationCode", code);
            if (inviteCodeEdtLayout.getVisibility() == View.VISIBLE) {
                params.put("YaoQingMa", inviteCodeEdt.getText().toString());
            }
            mPresenter.userLogin(params, null);
        });

        mPresenter.onViewClick(loginForWX, v -> ThirdShareUtil.requireWXUserInfo());

        mPresenter.onViewClick(loginForQQ, v-> ThirdShareUtil.requireQQUserInfo(this, new ThirdShareUtil.QQRequestListener() {
            @Override
            public void onComplete(QQTokenBean bean) {
                mPresenter.requestQQUserInfo(new HashMap<String, String>(){
                    {
                        put("access_token", bean.getAccess_token());
                        put("oauth_consumer_key", BuildConfig.QQ_ID);
                        put("openid", bean.getOpenid());
                        put("format", "json");
                    }
                });
            }

            @Override
            public void onError(UiError uiError) {
                showMessage("授权失败");
            }

            @Override
            public void onCancel() {
                showMessage("已取消QQ登录");
            }
        }));

        mPresenter.onViewClick(loginForWB, v-> {
            showLoadingDialog(true);
            ThirdShareUtil.requireSinaUserInfo(this, new WbAuthListener() {
                @Override
                public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
                    if (oauth2AccessToken.isSessionValid()) {
                        mPresenter.requestSinaUserInfo(oauth2AccessToken);
                    } else {
                        cancelLoadingDialog();
                        showMessage("获取微博授权失败");
                    }
                }

                @Override
                public void cancel() {
                    cancelLoadingDialog();
                    showMessage("微博登录已取消");
                }

                @Override
                public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
                    cancelLoadingDialog();
                    showMessage(wbConnectErrorMessage.getErrorMessage());
                }
            });
        });
    }

    private void goNextView(String s) {
        msgCode.cleartext();
        msgTable.setText("验证码发送至 +86 " + s);
        codeLayout.setVisibility(View.VISIBLE);
        requestMsg.setVisibility(View.GONE);
        txTimer.setText("60s后重新发送");
        txTimer.setEnabled(false);
        mPresenter.requestCode(s);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    public void eventHandler(NormalEvent normalEvent) {
        switch (normalEvent.getCode()) {
            case NormalEvent.WX_LOGIN:
                Map<String, String> params = new HashMap<>();
                params.put("WeiXinAccount", normalEvent.getParam("openId"));
                params.put("NickName", normalEvent.getParam("nickName"));
                if (inviteCodeEdtLayout.getVisibility() == View.VISIBLE) {
                    params.put("YaoQingMa", inviteCodeEdt.getText().toString());
                }
                mPresenter.userLogin(params, normalEvent.getParam("picUrl"));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {
            ThirdShareUtil.handlerTencentResult(data);
        } else {
            ThirdShareUtil.handlerSinaresult(requestCode,resultCode,data);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThirdShareUtil.clearTencent(this);
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
            case BaseResponseBean.SUCCESS_CODE:
                showMessage("验证码已发送");
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
}

























