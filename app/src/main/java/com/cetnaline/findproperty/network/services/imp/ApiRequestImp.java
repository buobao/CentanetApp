package com.cetnaline.findproperty.network.services.imp;

import com.cetnaline.findproperty.BuildConfig;
import com.cetnaline.findproperty.network.NetWorkManager;
import com.cetnaline.findproperty.network.bean.BaseResponseBean;
import com.cetnaline.findproperty.utils.RxUtil;

import io.reactivex.Observable;

public class ApiRequestImp {
    /**
     * 获取服务器地址
     * @return
     */
    public static Observable<BaseResponseBean<String>> getAppServiceAddressRequest() {
        String[] arr = BuildConfig.VERSION_NAME.split("-");
        return NetWorkManager.getInstance().getCentalineRequest().getAppServiceAddressRequest("APP_ANDROID_APUSH", arr[0])
                .compose(RxUtil.applyIoSchedulers());
    }

}
