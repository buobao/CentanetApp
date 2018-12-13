package com.cetnaline.findproperty.ui.login;

import com.cetnaline.findproperty.base.BaseView;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;

public interface LoginView extends BaseView {
    void setRequestCodeResult(BaseResponseBean<Integer> responseBean);
    void updateTimerMsg(long currentSecond);
}
