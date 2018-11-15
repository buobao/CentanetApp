package com.cetnaline.findproperty.network.services;

import com.cetnaline.findproperty.network.bean.BaseResponseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequestService {

    /**
     * 获取服务器地址
     * @param appName
     * @param versionCode
     * @return
     */
    @GET("GetAppServiceAddressRequest")
    Observable<BaseResponseBean<String>> getAppServiceAddressRequest(@Query("AppName") String appName, @Query("VersionCode") String versionCode);
}
