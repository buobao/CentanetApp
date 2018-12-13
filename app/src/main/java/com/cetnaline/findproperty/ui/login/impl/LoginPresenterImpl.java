package com.cetnaline.findproperty.ui.login.impl;

import com.cetnaline.findproperty.base.BasePresenter;
import com.cetnaline.findproperty.model.network.services.imp.ApiRequestImp;
import com.cetnaline.findproperty.ui.login.LoginPresenter;
import com.cetnaline.findproperty.ui.login.LoginView;
import com.cetnaline.findproperty.utils.RxUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class LoginPresenterImpl extends BasePresenter<LoginView> implements LoginPresenter {

    private Disposable timerDisposable;

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

    @Override
    public void canceltimer() {
        if (timerDisposable != null && !timerDisposable.isDisposed()) {
            timerDisposable.dispose();
        }
    }
}
