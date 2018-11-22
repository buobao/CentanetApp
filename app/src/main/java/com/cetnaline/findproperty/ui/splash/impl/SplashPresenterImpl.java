package com.cetnaline.findproperty.ui.splash.impl;

import android.content.Context;

import com.cetnaline.findproperty.base.BasePresenter;
import com.cetnaline.findproperty.model.cache.CacheHolder;
import com.cetnaline.findproperty.model.database.DatabaseHelper;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;
import com.cetnaline.findproperty.model.network.services.imp.ApiRequestImp;
import com.cetnaline.findproperty.model.sp.SpContents;
import com.cetnaline.findproperty.model.sp.SpHelper;
import com.cetnaline.findproperty.ui.splash.SplashPresenter;
import com.cetnaline.findproperty.ui.splash.SplashView;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.functions.Function5;

public class SplashPresenterImpl extends BasePresenter<SplashView> implements SplashPresenter {
    @Override
    public void getAppHost() {
        addDisposable(ApiRequestImp.getAppServiceAddressRequest().subscribe(stringBaseResponseBean -> {
            iView.updateAppHost(stringBaseResponseBean.getResult());
        }, throwable -> {
            throwable.printStackTrace();
            iView.showMessage(throwable.getMessage());
        }));
    }

    @Override
    public void getAppBaseData() {

    }

    @Override
    public void checkDatabaseVersion(Context context) {
        addDisposable(ApiRequestImp.getDataVersion().subscribe(longBaseResponseBean -> {
            if (SpHelper.getInstance(context).getLong(SpContents.BASE_DATA_VERSION) < longBaseResponseBean.getResult() || isDatabaseUpdate(context)) {
                SpHelper.getInstance(context).saveLong(SpContents.BASE_DATA_VERSION, longBaseResponseBean.getResult());
                //加载服务器数据
                // TODO: 2018/11/22  
//                addDisposable(Observable.zip(ApiRequestImp.getGscopes(),
//                        ApiRequestImp.getRailLines(),
//                        ApiRequestImp.getSchoolList("0"),
//                        ApiRequestImp.getSearchData(),
//                        ApiRequestImp.getStores(new HashMap() {{
//                            put("PageIndex", "1");
//                            put("PageCount", "10000");
//                        }}, new Function5<>())));
            }
        }, throwable -> {
            if (CacheHolder.getInstance().isFirstOpen()) {
                CacheHolder.getInstance().setFirstOpen(false);
                loadLocalData();
            }
            throwable.printStackTrace();
        }));


    }

    private boolean isDatabaseUpdate(Context context) {
        int dataVersion = DatabaseHelper.getInstance(context).getDbVersion();
        if (dataVersion != CacheHolder.getInstance().getDbVersion()) {
            CacheHolder.getInstance().setDbVersion(dataVersion);
            CacheHolder.getInstance().clearInitDataStatus();
            return true;
        }
        return false;
    }

    /**
     * 加载本地预装数据
     */
    private void loadLocalData() {

    }
}
