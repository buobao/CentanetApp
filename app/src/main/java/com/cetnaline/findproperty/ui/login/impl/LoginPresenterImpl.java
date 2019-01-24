package com.cetnaline.findproperty.ui.login.impl;

import android.text.TextUtils;

import com.cetnaline.findproperty.base.BasePresenter;
import com.cetnaline.findproperty.bus.RxBus;
import com.cetnaline.findproperty.bus.events.NormalEvent;
import com.cetnaline.findproperty.model.cache.CacheHolder;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;
import com.cetnaline.findproperty.model.network.services.imp.ApiRequestImp;
import com.cetnaline.findproperty.ui.login.LoginPresenter;
import com.cetnaline.findproperty.ui.login.LoginView;
import com.cetnaline.findproperty.utils.RxUtil;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class LoginPresenterImpl extends BasePresenter<LoginView> implements LoginPresenter {

    private Disposable timerDisposable;

    /**
     * 请求验证码
     * @param phone
     */
    @Override
    public void requestCode(String phone) {
        addDisposable(ApiRequestImp.getMessageCode(phone)
                .subscribe(integerBaseResponseBean -> iView.setRequestCodeResult(integerBaseResponseBean)));

        timerDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(60)
                .compose(RxUtil.applyIoSchedulers())
                .subscribe(aLong -> {
                    iView.updateTimerMsg(aLong);
                }, throwable -> throwable.printStackTrace());

        addDisposable(timerDisposable);
    }

    /**
     * 取消计时器
     */
    @Override
    public void canceltimer() {
        if (timerDisposable != null && !timerDisposable.isDisposed()) {
            timerDisposable.dispose();
        }
    }

    /**
     * 用户登录请求
     * @param params
     */
    @Override
    public void userLogin(Map<String, String> params, String headImg) {
        iView.showLoadingDialog(true);

        addDisposable(ApiRequestImp.login(params)
                    .subscribe(userInfoBeanBaseResponseBean -> {
                        switch (userInfoBeanBaseResponseBean.getResultNo()) {
                            case BaseResponseBean.FAILE_CODE:
                                iView.cancelLoadingDialog();
                                if (!TextUtils.isEmpty(userInfoBeanBaseResponseBean.getMessage())) {
                                    iView.showMessage(userInfoBeanBaseResponseBean.getMessage());
                                } else {
                                    iView.showMessage("登录失败");
                                }
                                break;
                            case BaseResponseBean.REQUEST_ERROR_CODE:
                                iView.cancelLoadingDialog();
                                iView.showMessage("服务器请求异常");
                                break;
                            case BaseResponseBean.REQUEST_OVERTIME_CODE:
                                iView.cancelLoadingDialog();
                                iView.showMessage("网络请求超时");
                                break;
                            case BaseResponseBean.REQUEST_NOT_CONNECTION_CODE:
                                iView.cancelLoadingDialog();
                                iView.showMessage("服务器无法连接，请检查网络");
                                break;
                            case BaseResponseBean.SUCCESS_CODE:
                                if (TextUtils.isEmpty(userInfoBeanBaseResponseBean.getResult().getUserPhotoUrl())) {
                                    userInfoBeanBaseResponseBean.getResult().setUserPhotoUrl(headImg);
                                }

                                //获取融云token
                                addDisposable(ApiRequestImp.getToken("u_" + userInfoBeanBaseResponseBean.getResult().getUserId().toLowerCase(),
                                        userInfoBeanBaseResponseBean.getResult().getNickName(),
                                        userInfoBeanBaseResponseBean.getResult().getUserPhotoUrl())
                                        .subscribe(rcTokenBean -> {
                                            iView.cancelLoadingDialog();
                                            iView.showMessage("登录成功");
                                            CacheHolder.getInstance().setRcToken(rcTokenBean.getToken());
                                            CacheHolder.getInstance().setCurrentUserInfo(userInfoBeanBaseResponseBean.getResult()); //保存当前用户登录数据
                                            //这里用于通知登录服务器成功刷新相关用户数据，融云连接成功事件请查看RongUtil
                                            RxBus.getInstance().post(new NormalEvent(NormalEvent.LOGIN_SUCCESS)); //发送登录成功事件
                                            iView.finishView();
                                        }, throwable -> {
                                            iView.cancelLoadingDialog();
                                            iView.showMessage("连接融云失败");
                                            throwable.printStackTrace();
                                        }));

                                break;
                        }
                    }));





    }

    @Override
    public void requestQQUserInfo(Map<String, String> params) {
        iView.showLoadingDialog(true);
        addDisposable(ApiRequestImp.getQqUserInfo(params)
        .subscribe(qqUserInfoBean -> userLogin(new HashMap() {
            {
                put("QQAccount", params.get("openid"));
                put("NickName", qqUserInfoBean.getNickname() == null ? "" : qqUserInfoBean.getNickname());
                // TODO: 2018/12/14 邀请码登录 传入
//                    String yaoqingma = SharedPreferencesUtil.getString(AppContents.YAO_QING_MA);
//                    if (yaoqingma != null && !"".equals(yaoqingma.trim()) && !"null".equals(yaoqingma)) {
//                        put("YaoQingMa", yaoqingma);
//                    }
            }}, qqUserInfoBean.getFigureurl()), throwable -> {
            iView.cancelLoadingDialog();
            iView.showMessage("未能获取授权");
            throwable.printStackTrace();
        }));

    }

    @Override
    public void requestSinaUserInfo(Oauth2AccessToken accessToken) {
        addDisposable(ApiRequestImp.getSinaUserInfo(accessToken.getToken(), accessToken.getUid(), "")
        .subscribe(sinaUserInfoBean -> userLogin(new HashMap() {
            {
                put("SinaAccount", sinaUserInfoBean.getIdstr());
                put("NickName", sinaUserInfoBean.getScreen_name() == null ? "":sinaUserInfoBean.getScreen_name());
                // TODO: 2018/12/14 邀请码登录 传入
//                    String yaoqingma = SharedPreferencesUtil.getString(AppContents.YAO_QING_MA);
//                    if (yaoqingma != null && !"".equals(yaoqingma.trim()) && !"null".equals(yaoqingma)) {
//                        put("YaoQingMa", yaoqingma);
//                    }
            }}, sinaUserInfoBean.getAvatar_hd()), throwable -> {
            iView.cancelLoadingDialog();
            iView.showMessage("微博授权登录失败");
            throwable.printStackTrace();
        }));
    }

}
