package com.cetnaline.findproperty.base;

import android.view.View;

public interface IPresenter<T extends IView> {
    void attachView(T view);
    void detachView();
    void eventhandler();
    void onViewClick(View target, View.OnClickListener listener);
}
