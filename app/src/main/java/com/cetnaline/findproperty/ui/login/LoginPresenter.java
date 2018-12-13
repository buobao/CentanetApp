package com.cetnaline.findproperty.ui.login;

import com.cetnaline.findproperty.base.IPresenter;

public interface LoginPresenter extends IPresenter<LoginView> {
    void requestCode(String phone);
    void canceltimer();
}
