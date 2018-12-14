package com.cetnaline.findproperty.base;

import android.view.View;

public interface IPresenter<T extends IView> {
    void attachView(T view);
    void detachView();
    void eventHandler();
    void onViewClick(View target, View.OnClickListener listener);
}
