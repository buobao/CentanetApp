package com.cetnaline.findproperty.base;

import android.support.annotation.StringRes;

import com.cetnaline.findproperty.bus.events.NormalEvent;

public interface BaseView extends IView {
    void showMessage(String msg);
    void showMessage(@StringRes int msgId);
    void showLoadingDialog(boolean cancelable);
    void cancelLoadingDialog();

    void connectNetworkHandler();
    void disConnectNetworkHandler();
    void eventHandler(NormalEvent normalEvent);
}
