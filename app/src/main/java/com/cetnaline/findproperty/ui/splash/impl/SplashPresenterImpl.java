package com.cetnaline.findproperty.ui.splash.impl;

import android.content.Context;
import android.util.Log;

import com.cetnaline.findproperty.base.BasePresenter;
import com.cetnaline.findproperty.model.cache.CacheHolder;
import com.cetnaline.findproperty.model.database.DatabaseHelper;
import com.cetnaline.findproperty.model.database.entity.GScope;
import com.cetnaline.findproperty.model.database.entity.RailLine;
import com.cetnaline.findproperty.model.database.entity.School;
import com.cetnaline.findproperty.model.database.entity.Store;
import com.cetnaline.findproperty.model.network.bean.BaseResponseBean;
import com.cetnaline.findproperty.model.network.bean.responsebean.SearchMenuBean;
import com.cetnaline.findproperty.model.network.services.imp.ApiRequestImp;
import com.cetnaline.findproperty.model.sp.SpContents;
import com.cetnaline.findproperty.model.sp.SpHelper;
import com.cetnaline.findproperty.ui.splash.SplashPresenter;
import com.cetnaline.findproperty.ui.splash.SplashView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function5;

public class SplashPresenterImpl extends BasePresenter<SplashView> implements SplashPresenter {
    @Override
    public void getAppHost() {
        addDisposable(ApiRequestImp.getAppServiceAddressRequest().subscribe(stringBaseResponseBean -> {
            if (stringBaseResponseBean.getResultNo() == BaseResponseBean.SUCCESS_CODE) {
                iView.updateAppHost(stringBaseResponseBean.getResult());
            } else if (stringBaseResponseBean.getResultNo() == BaseResponseBean.FAILE_CODE){
                iView.showMessage("请求服务器地址失败");
            } else if (stringBaseResponseBean.getResultNo() == BaseResponseBean.REQUEST_OVERTIME_CODE) {
                iView.showMessage("请求超时，请检查网络");
            } else {
                iView.showMessage("服务器内部错误");
            }
        }));
    }

    @Override
    public void getAppBaseData() {

    }

    @Override
    public void checkDatabaseVersion(Context context) {
        addDisposable(ApiRequestImp.getDataVersion().subscribe(longBaseResponseBean -> {
            if (longBaseResponseBean.getResultNo() != 0) {
                if (CacheHolder.getInstance().isFirstOpen()) {
                    CacheHolder.getInstance().setFirstOpen(false);
                    loadJsonGscope(context);
                    loadJsonStore(context);
                    loadJsonSearchData(context);
                    loadJsonSchool(context);
                    loadJsonRailLines(context);
//                    iView.showMessage(DatabaseHelper.getInstance().getRailLines().size()+"");
                }
            } else {
                if (SpHelper.getInstance(context).getLong(SpContents.BASE_DATA_VERSION) < longBaseResponseBean.getResult() ||   //服务器数据更新
                        isDatabaseUpdate() ||     //本地数据库升级
                        !isAllDataUpdated()) {     //所有数据是否都已加载完毕
                    if (SpHelper.getInstance(context).getLong(SpContents.BASE_DATA_VERSION) < longBaseResponseBean.getResult() ||
                            isDatabaseUpdate()) {
                        CacheHolder.getInstance().clearInitDataStatus();
                    }
                    SpHelper.getInstance(context).saveLong(SpContents.BASE_DATA_VERSION, longBaseResponseBean.getResult());
                    //加载服务器数据
                    addDisposable(Observable.zip(ApiRequestImp.getGscopes(),
                            ApiRequestImp.getRailLines(),
                            ApiRequestImp.getSchoolList("0"),
                            ApiRequestImp.getSearchData(),
                            ApiRequestImp.getStores(new HashMap() {{
                                put("PageIndex", "1");
                                put("PageCount", "10000");
                            }}), (Function5<BaseResponseBean<List<GScope>>,
                                    BaseResponseBean<List<RailLine>>,
                                    BaseResponseBean<List<School>>,
                                    BaseResponseBean<List<SearchMenuBean>>,
                                    BaseResponseBean<List<Store>>, Boolean>)
                                    (listBaseResponseBean, listBaseResponseBean2,
                                     listBaseResponseBean3, listBaseResponseBean4,
                                     listBaseResponseBean5) -> {
                                        if (!CacheHolder.getInstance().isgScopeDataLoaded()) {
                                            if (listBaseResponseBean.getResultNo() == BaseResponseBean.SUCCESS_CODE) {
                                                CacheHolder.getInstance().setgScopeDataLoaded(true);
                                                //保存
                                                DatabaseHelper.getInstance().insertGscopes(listBaseResponseBean.getResult());
//                                            iView.showMessage(DatabaseHelper.getInstance().getChildGscopes(21).size()+"");
                                            } else {
                                                if (!CacheHolder.getInstance().isgScopeDataJsonLoaded()) {
                                                    //load json
                                                    loadJsonGscope(context);
                                                }
                                            }
                                        }
                                        if (!CacheHolder.getInstance().isRailLineDataLoaded()) {
                                            if (listBaseResponseBean2.getResultNo() == BaseResponseBean.SUCCESS_CODE) {
                                                CacheHolder.getInstance().setRailLineDataLoaded(true);
                                                DatabaseHelper.getInstance().insertRailLines(listBaseResponseBean2.getResult());
//                                            iView.showMessage(DatabaseHelper.getInstance().getRailLines().size()+"");
                                            } else {
                                                if (!CacheHolder.getInstance().isRailLineDataJsonLoaded()) {
                                                    //load json
                                                    loadJsonRailLines(context);
                                                    iView.showMessage(DatabaseHelper.getInstance().getRailLines().size() + "");
                                                }
                                            }
                                        }
                                        if (!CacheHolder.getInstance().isSchoolDataLoaded()) {
                                            if (listBaseResponseBean3.getResultNo() == BaseResponseBean.SUCCESS_CODE) {
                                                CacheHolder.getInstance().setSchoolDataLoaded(true);
                                                DatabaseHelper.getInstance().insertSchools(listBaseResponseBean3.getResult());
//                                            iView.showMessage(DatabaseHelper.getInstance().getSchools().size()+"");
                                            } else {
                                                if (!CacheHolder.getInstance().isSchoolDataJsonLoaded()) {
                                                    //load json
                                                    loadJsonSchool(context);
                                                }
                                            }
                                        }
                                        if (!CacheHolder.getInstance().isSearchDataLoaded()) {
                                            if (listBaseResponseBean4.getResultNo() == BaseResponseBean.SUCCESS_CODE) {
                                                CacheHolder.getInstance().setSearchDataLoaded(true);
                                                DatabaseHelper.getInstance().inseartSearchDatas(listBaseResponseBean4.getResult());
                                            } else {
                                                if (!CacheHolder.getInstance().isSearchDataJsonLoaded()) {
                                                    //load json
                                                    loadJsonSearchData(context);
                                                }
                                            }
                                        }
                                        if (!CacheHolder.getInstance().isStoreDataLoaded()) {
                                            if (listBaseResponseBean5.getResultNo() == BaseResponseBean.SUCCESS_CODE) {
                                                CacheHolder.getInstance().setStoreDataLoaded(true);
                                                DatabaseHelper.getInstance().insertStores(listBaseResponseBean5.getResult());
//                                            iView.showMessage(DatabaseHelper.getInstance().getStores().size()+"");
                                            } else {
                                                if (!CacheHolder.getInstance().isStoreDataJsonLoaded()) {
                                                    //load json
                                                    loadJsonStore(context);
                                                }
                                            }
                                        }
                                        return true;
                                    }).subscribe(o -> {
                        Log.i("splash", "load success!");
                    }));
                }
            }
        }));
    }

    private boolean isDatabaseUpdate() {
        int dataVersion = DatabaseHelper.getInstance().getDbVersion();
        if (dataVersion != CacheHolder.getInstance().getDbVersion()) {
            CacheHolder.getInstance().setDbVersion(dataVersion);
            CacheHolder.getInstance().clearInitDataStatus();
            return true;
        }
        return false;
    }

    /**
     * 是否所有数据全部加载完毕
     * @return
     */
    private boolean isAllDataUpdated() {
        return CacheHolder.getInstance().isgScopeDataLoaded() &&
                CacheHolder.getInstance().isRailLineDataLoaded() &&
                CacheHolder.getInstance().isSchoolDataLoaded() &&
                CacheHolder.getInstance().isSearchDataLoaded() &&
                CacheHolder.getInstance().isStoreDataLoaded();
    }

    /**
     * 加载gscope json
     * @param context
     * @return
     */
    private void loadJsonGscope(Context context) {
        try {
            String jsonString;
            String resultString="";
            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open("json/gscope.json"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            while ((jsonString=bufReader.readLine())!=null) {
                resultString+=jsonString;
            }
            Gson gson = new Gson();
            List<GScope> gScopeBeanList = gson.fromJson(resultString, new TypeToken<List<GScope>>() {}.getType());
            DatabaseHelper.getInstance().insertGscopes(gScopeBeanList);
            CacheHolder.getInstance().setgScopeDataJsonLoaded(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadJsonRailLines(Context context) {
        try {
            String jsonString;
            String resultString="";
            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open("json/railLines.json"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            while ((jsonString=bufReader.readLine())!=null) {
                resultString+=jsonString;
            }
            Gson gson = new Gson();
            List<RailLine> railLineList = gson.fromJson(resultString, new TypeToken<List<RailLine>>() {}.getType());
            DatabaseHelper.getInstance().insertRailLines(railLineList);
            CacheHolder.getInstance().setRailLineDataJsonLoaded(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadJsonSchool(Context context) {
        try {
            String jsonString;
            String resultString="";
            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open("json/school.json"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            while ((jsonString=bufReader.readLine())!=null) {
                resultString+=jsonString;
            }
            Gson gson = new Gson();
            List<School> schoolBoList = gson.fromJson(resultString, new TypeToken<List<School>>() {}.getType());
            DatabaseHelper.getInstance().insertSchools(schoolBoList);
            CacheHolder.getInstance().setSchoolDataJsonLoaded(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadJsonSearchData(Context context) {
        try {
            String jsonString;
            String resultString="";
            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open("json/searchData.json"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            while ((jsonString=bufReader.readLine())!=null) {
                resultString+=jsonString;
            }
            Gson gson = new Gson();
            List<SearchMenuBean> searchDataList = gson.fromJson(resultString, new TypeToken<List<SearchMenuBean>>() {}.getType());
            DatabaseHelper.getInstance().inseartSearchDatas(searchDataList);
            CacheHolder.getInstance().setSearchDataJsonLoaded(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadJsonStore(Context context) {
        try {
            String jsonString;
            String resultString="";
            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open("json/store.json"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            while ((jsonString=bufReader.readLine())!=null) {
                resultString+=jsonString;
            }
            Gson gson = new Gson();
            List<Store> storeBoList = gson.fromJson(resultString, new TypeToken<List<Store>>() {}.getType());
            DatabaseHelper.getInstance().insertStores(storeBoList);
            CacheHolder.getInstance().setStoreDataJsonLoaded(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
