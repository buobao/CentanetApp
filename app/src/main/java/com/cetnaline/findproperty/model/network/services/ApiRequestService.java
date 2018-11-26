package com.cetnaline.findproperty.model.network.services;

import com.cetnaline.findproperty.model.database.entity.GScope;
import com.cetnaline.findproperty.model.database.entity.School;
import com.cetnaline.findproperty.model.network.bean.responsebean.NewHouseGScopeBean;
import com.cetnaline.findproperty.model.database.entity.RailLine;
import com.cetnaline.findproperty.model.database.entity.Store;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.SearchMenuBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
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
}

























