package com.cetnaline.findproperty.model.network.services.imp;

import com.cetnaline.findproperty.BuildConfig;
import com.cetnaline.findproperty.model.database.entity.GScope;
import com.cetnaline.findproperty.model.database.entity.RailLine;
import com.cetnaline.findproperty.model.database.entity.School;
import com.cetnaline.findproperty.model.database.entity.Store;
import com.cetnaline.findproperty.model.network.NetWorkContents;
import com.cetnaline.findproperty.model.network.NetWorkManager;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.SearchMenuBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.UserInfoBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.WXTokenBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.WxUserBean;
import com.cetnaline.findproperty.utils.ApplicationUtil;
import com.cetnaline.findproperty.utils.RxUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class ApiRequestImp {
    /**
     * 获取服务器地址
     * @return
     */
    public static Observable<BaseResponseBean<String>> getAppServiceAddressRequest() {
        String[] arr = BuildConfig.VERSION_NAME.split("-");
        return NetWorkManager.getInstance().getCacheCentalineRequest()
                .getAppServiceAddressRequest("APP_ANDROID_APUSH", arr[0])
                .compose(RxUtil.applyIoSchedulers())
                .onErrorResumeNext(RxUtil.requestErrorHandler());
    }

    /**
     * 获取基础数据版本号
     * @return
     */
    public static Observable<BaseResponseBean<Long>> getDataVersion() {
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getDataVersion()
                .compose(RxUtil.applyIoSchedulers())
                .onErrorResumeNext(RxUtil.requestErrorHandler());
    }

    /**
     * 获取区域、板块信息
     * @return
     */
    public static Observable<BaseResponseBean<List<GScope>>> getGscopes() {
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getGScope()
                .compose(RxUtil.applyIoSchedulers())
                .onErrorResumeNext(RxUtil.requestErrorHandler());
    }

    /**
     * 获取地铁数据
     * @return
     */
    public static Observable<BaseResponseBean<List<RailLine>>> getRailLines() {
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getRailLines()
                .compose(RxUtil.applyIoSchedulers())
                .onErrorResumeNext(RxUtil.requestErrorHandler());
    }

    /**
     * 获取门店信息
     * @param params
     * @return
     */
    public static Observable<BaseResponseBean<List<Store>>> getStores(@QueryMap(encoded = true) Map<String, String> params){
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getStores(params)
                .compose(RxUtil.applyIoSchedulers())
                .onErrorResumeNext(RxUtil.requestErrorHandler());
    }

    /**
     * 获取学校信息
     * @param regionId
     * @return
     */
    public static Observable<BaseResponseBean<List<School>>> getSchoolList(@Query("regionId") String regionId){
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getSchools(regionId)
                .compose(RxUtil.applyIoSchedulers())
                .onErrorResumeNext(RxUtil.requestErrorHandler());
    }

    /**
     * 获取搜索菜单
     * @return
     */
    public static Observable<BaseResponseBean<List<SearchMenuBean>>> getSearchData(){
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getSearchData()
                .compose(RxUtil.applyIoSchedulers())
                .onErrorResumeNext(RxUtil.requestErrorHandler());
    }

    /**
     * 获取短信验证码
     * @param phone
     * @return
     */
    public static Observable<BaseResponseBean<Integer>> getMessageCode(String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("Mobile", phone);
        params.put("AppNo", NetWorkContents.APP_NO);
        params.put("CityCode", NetWorkContents.CITY_CODE);
        String sign = ApplicationUtil.md5Encode(NetWorkContents.PRIVATESECRET, "021", phone, NetWorkContents.PUBLICSECRET);
        params.put("Sign", sign);
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getMessageCode(params)
                .compose(RxUtil.applyIoSchedulers())
                .onErrorResumeNext(RxUtil.requestErrorHandler());
    }

    /**
     * 用户登录
     * @param params
     * @return
     */
    public static Observable<BaseResponseBean<UserInfoBean>> login(Map<String, String> params) {
        params.put("AppNo", NetWorkContents.APP_NO);
        params.put("CityCode", NetWorkContents.CITY_CODE);
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .login(params)
                .compose(RxUtil.applyIoSchedulers())
                .onErrorResumeNext(RxUtil.requestErrorHandler());
    }

    /**
     * 微信登录 获取token
     * @param code
     * @return
     */
    public static Observable<WXTokenBean> getUserToken(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", BuildConfig.APP_ID_WX);
        params.put("secret", BuildConfig.APP_SECRET_WX);
        params.put("code", code);
        params.put("grant_type", "authorization_code");

        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getUserToken(params)
                .compose(RxUtil.applyIoSchedulers());
    }

    /**
     * 获取微信用户信息
     * @param token
     * @param openid
     * @return
     */
    public static Observable<WxUserBean> getWxUserInfo(String token, String openid) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", token);
        params.put("openid", openid);

        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getWxUserInfo(params)
                .compose(RxUtil.applyIoSchedulers());
    }

}
























