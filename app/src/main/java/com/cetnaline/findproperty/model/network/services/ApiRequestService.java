package com.cetnaline.findproperty.model.network.services;

import com.cetnaline.findproperty.model.database.entity.GScope;
import com.cetnaline.findproperty.model.database.entity.RailLine;
import com.cetnaline.findproperty.model.database.entity.School;
import com.cetnaline.findproperty.model.database.entity.Store;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.NewHouseGScopeBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.QQUserInfoBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.RcTokenBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.SearchMenuBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.SinaUserInfoBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.UserInfoBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.WXTokenBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.WxUserBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiRequestService {

    /**
     * 获取服务器地址
     * @param appName
     * @param versionCode
     * @return
     */
    @GET("GetAppServiceAddressRequest")
    Observable<BaseResponseBean<String>> getAppServiceAddressRequest(@Query("AppName") String appName, @Query("VersionCode") String versionCode);

    /**
     * 获取基础数据版本号
     * @return
     */
    @GET("GetVersion")
    Observable<BaseResponseBean<Long>> getDataVersion();

    /**
     * 获取区域、板块信息
     * @return
     */
    @GET("GetAllGScopeRequest")
    Observable<BaseResponseBean<List<GScope>>> getGScope();

    /**
     * 获取上海周边区域、板块信息
     * @return
     */
    @GET("DistrictEstRequest")
    Observable<BaseResponseBean<List<NewHouseGScopeBean>>> geDistrictEst();

    /**
     * 获取地铁数据
     */
    @GET("GetRailLinesRequest")
    Observable<BaseResponseBean<List<RailLine>>> getRailLines();

    /**
     * 获取门店信息
     * @param params
     * @return
     */
    @GET("SearchStoreSingleRequest")
    Observable<BaseResponseBean<List<Store>>> getStores(@QueryMap(encoded = true) Map<String, String> params);

    /**
     * 获取学校信息
     * @param regionId
     * @return
     */
    @GET("GetSchoolInfosRequest?PageIndex=1&PageCount=10000&IsLoadData=false")
    Observable<BaseResponseBean<List<School>>> getSchools(@Query("regionId") String regionId);


    //搜索 菜单相关数据
    @GET("SearchDataRequest")
    Observable<BaseResponseBean<List<SearchMenuBean>>> getSearchData();

    /**
     * 发送验证码
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("SmsRequest")
    Observable<BaseResponseBean<Integer>> getMessageCode(@FieldMap Map<String, String> params);

    /**
     * 用户登录
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("UserLogin2Request")
    Observable<BaseResponseBean<UserInfoBean>> login(@FieldMap Map<String, String> params);

    /**
     * 微信接口，获取token
     */
    @GET("https://api.weixin.qq.com/sns/oauth2/access_token")
    Observable<WXTokenBean> getUserToken(@QueryMap(encoded = true) Map<String, String> params);

    /**
     * 获取微信用户信息
     * @param params
     * @return
     */
    @GET("https://api.weixin.qq.com/sns/userinfo")
    Observable<WxUserBean> getWxUserInfo(@QueryMap(encoded = true) Map<String, String> params);

    /**
     * 获取qq用户信息
     * @param params
     * @return
     */
    @GET("https://graph.qq.com/user/get_simple_userinfo")
    Observable<QQUserInfoBean> getQqUserInfo(@QueryMap(encoded = true) Map<String, String> params);


    /**
     * 获取sina用户信息
     */
    @GET("https://api.weibo.com/2/users/show.json")
    Observable<SinaUserInfoBean> getSinaUserInfo(@QueryMap(encoded = true) Map<String, String> params);

    /**
     * 获取融云token
     * @param userId 用户Id
     * @param name 用户名
     * @param portraitUri 用户头像路径
     * @return
     */
    @FormUrlEncoded
    @POST("https://api.cn.ronghub.com/user/getToken.json")
    Observable<RcTokenBean> getToken(@Field("userId") String userId, @Field("name") String name, @Field("portraitUri") String portraitUri,
                                     @Header("App-Key")String appKey,
                                     @Header("Nonce")String nonce,
                                     @Header("Timestamp")String timeStamp,
                                     @Header("Signature")String signature);


}

























