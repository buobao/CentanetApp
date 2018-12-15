package com.cetnaline.findproperty.ui.login;

import com.cetnaline.findproperty.base.BaseView;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;

public interface LoginView extends BaseView {
    //验证码获取结果处理
    void setRequestCodeResult(BaseResponseBean<Integer> responseBean);
    //更新计时器
    void updateTimerMsg(long currentSecond);
}
