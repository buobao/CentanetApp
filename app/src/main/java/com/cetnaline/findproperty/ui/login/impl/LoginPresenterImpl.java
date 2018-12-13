package com.cetnaline.findproperty.ui.login.impl;

import com.cetnaline.findproperty.base.BasePresenter;
import com.cetnaline.findproperty.model.network.services.imp.ApiRequestImp;
import com.cetnaline.findproperty.ui.login.LoginPresenter;
import com.cetnaline.findproperty.ui.login.LoginView;
import com.cetnaline.findproperty.utils.RxUtil;

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
    public void userLogin(Map<String, String> params) {
        iView.showLoadingDialog(false);
        addDisposable(ApiRequestImp.login(params)
                .subscribe(userInfoBeanBaseResponseBean -> {
                    iView.cancelLoadingDialog();
                    iView.loginfinish(userInfoBeanBaseResponseBean);
                }));
    }
}
