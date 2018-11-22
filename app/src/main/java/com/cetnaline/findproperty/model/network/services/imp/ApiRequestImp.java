package com.cetnaline.findproperty.model.network.services.imp;

import com.cetnaline.findproperty.BuildConfig;
import com.cetnaline.findproperty.model.database.entity.GScope;
import com.cetnaline.findproperty.model.database.entity.RailLine;
import com.cetnaline.findproperty.model.database.entity.School;
import com.cetnaline.findproperty.model.database.entity.Store;
import com.cetnaline.findproperty.model.network.NetWorkManager;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.SearchMenuBean;
import com.cetnaline.findproperty.utils.RxUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
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
                .compose(RxUtil.applyIoSchedulers());
    }

    /**
     * 获取基础数据版本号
     * @return
     */
    public static Observable<BaseResponseBean<Long>> getDataVersion() {
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getDataVersion()
                .compose(RxUtil.applyIoSchedulers());
    }

    /**
     * 获取区域、板块信息
     * @return
     */
    public static Observable<BaseResponseBean<List<GScope>>> getGscopes() {
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getGScope()
                .compose(RxUtil.applyIoSchedulers());
    }

    /**
     * 获取地铁数据
     * @return
     */
    public static Observable<BaseResponseBean<List<RailLine>>> getRailLines() {
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getRailLines()
                .compose(RxUtil.applyIoSchedulers());
    }

    /**
     * 获取门店信息
     * @param params
     * @return
     */
    public static Observable<BaseResponseBean<List<Store>>> getStores(@QueryMap(encoded = true) Map<String, String> params){
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getStores(params)
                .compose(RxUtil.applyIoSchedulers());
    }

    /**
     * 获取学校信息
     * @param regionId
     * @return
     */
    Observable<BaseResponseBean<List<School>>> getSchoolList(@Query("regionId") String regionId){
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getSchools(regionId)
                .compose(RxUtil.applyIoSchedulers());
    }

    /**
     * 获取搜索菜单
     * @return
     */
    Observable<BaseResponseBean<List<SearchMenuBean>>> getSearchData(){
        return NetWorkManager.getInstance().getNoCacheCentalineRequest()
                .getSearchData()
                .compose(RxUtil.applyIoSchedulers());
    }
}
























