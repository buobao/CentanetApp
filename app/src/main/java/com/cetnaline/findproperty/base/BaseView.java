package com.cetnaline.findproperty.base;

import android.support.annotation.StringRes;

public interface BaseView extends IView {
    void showMessage(String msg);
    void showMessage(@StringRes int msgId);
}
