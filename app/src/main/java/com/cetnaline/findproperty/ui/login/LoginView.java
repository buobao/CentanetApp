package com.cetnaline.findproperty.ui.login;

import com.cetnaline.findproperty.base.BaseView;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.UserInfoBean;

public interface LoginView extends BaseView {
    //验证码获取结果处理
    void setRequestCodeResult(BaseResponseBean<Integer> responseBean);
    //更新计时器
    void updateTimerMsg(long currentSecond);
    //登录结果处理
    void loginfinish(BaseResponseBean<UserInfoBean> responseBean);
}
