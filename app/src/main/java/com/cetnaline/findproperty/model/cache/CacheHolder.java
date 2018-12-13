package com.cetnaline.findproperty.model.cache;

import android.content.Context;
import android.text.TextUtils;

import com.cetnaline.findproperty.model.network.bean.responsebean.UserInfoBean;
import com.cetnaline.findproperty.model.sp.SpContents;
import com.cetnaline.findproperty.model.sp.SpHelper;
import com.cetnaline.findproperty.utils.ApplicationUtil;

public class CacheHolder {

    private int dbVersion; //当前数据库版本
    private int guildVersion; //引导页版本号
    private boolean isFirstOpen; //首次打开
    private boolean networkStatus; //网络状态

    /**
     * 初始化数据加载状态
     */
    private boolean searchDataLoaded;
    private boolean districtEstLoaded;
    private boolean gScopeDataLoaded;
    private boolean railLineDataLoaded;
    private boolean storeDataLoaded;
    private boolean schoolDataLoaded;

    /**
     * 本地默认数据加载标志
     */
    private boolean searchDataJsonLoaded;
    private boolean districtEstJsonLoaded;
    private boolean gScopeDataJsonLoaded;
    private boolean railLineDataJsonLoaded;
    private boolean storeDataJsonLoaded;
    private boolean schoolDataJsonLoaded;

    private UserInfoBean currentUserInfo;

    private static CacheHolder cacheHolder;
    private static SpHelper spHelper;

    private CacheHolder() {

    }

    public static void init(Context context) {
        if (cacheHolder == null) {
            synchronized (CacheHolder.class) {
                if (cacheHolder == null) {
                    cacheHolder = new CacheHolder();
                    spHelper = SpHelper.getInstance(context);
                    //加载状态数据
                    cacheHolder.dbVersion = spHelper.getInt(SpContents.DB_VERSION, 0);
                    cacheHolder.guildVersion = spHelper.getInt(SpContents.GUILD_VERSION, 0);
                    cacheHolder.searchDataLoaded = spHelper.getBoolean(SpContents.SEARCHDATA_LOADED);
                    cacheHolder.districtEstLoaded = spHelper.getBoolean(SpContents.DISTRICTEST_LOADED);
                    cacheHolder.gScopeDataLoaded = spHelper.getBoolean(SpContents.GSCOPEDATA_LOADED);
                    cacheHolder.railLineDataLoaded = spHelper.getBoolean(SpContents.RAILLINEDATA_LOADED);
                    cacheHolder.storeDataLoaded = spHelper.getBoolean(SpContents.STOREDATA_LOADED);
                    cacheHolder.schoolDataLoaded = spHelper.getBoolean(SpContents.SCHOOLDATA_LOADED);

                    cacheHolder.searchDataJsonLoaded = spHelper.getBoolean(SpContents.SEARCHDATA_LOADED);
                    cacheHolder.districtEstJsonLoaded = spHelper.getBoolean(SpContents.DISTRICTEST_LOADED);
                    cacheHolder.gScopeDataJsonLoaded = spHelper.getBoolean(SpContents.GSCOPEDATA_LOADED);
                    cacheHolder.railLineDataJsonLoaded = spHelper.getBoolean(SpContents.RAILLINEDATA_LOADED);
                    cacheHolder.storeDataJsonLoaded = spHelper.getBoolean(SpContents.STOREDATA_LOADED);
                    cacheHolder.schoolDataJsonLoaded = spHelper.getBoolean(SpContents.SCHOOLDATA_LOADED);

                    cacheHolder.isFirstOpen = spHelper.getBoolean(SpContents.FIRST_OPEN);
                    cacheHolder.networkStatus = ApplicationUtil.checkNetwork(context);

                    cacheHolder.currentUserInfo = spHelper.getUserInfo();
                }
            }
        }
    }

    public static CacheHolder getInstance() {
        return cacheHolder;
    }

    public int getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(int dbVersion) {
        this.dbVersion = dbVersion;
        spHelper.saveInt(SpContents.DB_VERSION, dbVersion);
    }

    public int getGuildVersion() {
        return guildVersion;
    }

    public void setGuildVersion(int guildVersion) {
        this.guildVersion = guildVersion;
        spHelper.saveInt(SpContents.GUILD_VERSION, guildVersion);
    }

    public boolean isFirstOpen() {
        return !isFirstOpen;
    }

    public void setFirstOpen(boolean firstOpen) {
        isFirstOpen = !firstOpen;
        spHelper.saveBoolean(SpContents.FIRST_OPEN, !firstOpen);
    }

    public boolean isSearchDataLoaded() {
        return searchDataLoaded;
    }

    public void setSearchDataLoaded(boolean searchDataLoaded) {
        this.searchDataLoaded = searchDataLoaded;
        spHelper.saveBoolean(SpContents.SEARCHDATA_LOADED, searchDataLoaded);
    }

    public boolean isDistrictEstLoaded() {
        return districtEstLoaded;
    }

    public void setDistrictEstLoaded(boolean districtEstLoaded) {
        this.districtEstLoaded = districtEstLoaded;
        spHelper.saveBoolean(SpContents.DISTRICTEST_LOADED, districtEstLoaded);
    }

    public boolean isgScopeDataLoaded() {
        return gScopeDataLoaded;
    }

    public void setgScopeDataLoaded(boolean gScopeDataLoaded) {
        this.gScopeDataLoaded = gScopeDataLoaded;
        spHelper.saveBoolean(SpContents.GSCOPEDATA_LOADED, gScopeDataLoaded);
    }

    public boolean isRailLineDataLoaded() {
        return railLineDataLoaded;
    }

    public void setRailLineDataLoaded(boolean railLineDataLoaded) {
        this.railLineDataLoaded = railLineDataLoaded;
        spHelper.saveBoolean(SpContents.RAILLINEDATA_LOADED, railLineDataLoaded);
    }

    public boolean isStoreDataLoaded() {
        return storeDataLoaded;
    }

    public void setStoreDataLoaded(boolean storeDataLoaded) {
        this.storeDataLoaded = storeDataLoaded;
        spHelper.saveBoolean(SpContents.STOREDATA_LOADED, storeDataLoaded);
    }

    public boolean isSchoolDataLoaded() {
        return schoolDataLoaded;
    }

    public void setSchoolDataLoaded(boolean schoolDataLoaded) {
        this.schoolDataLoaded = schoolDataLoaded;
        spHelper.saveBoolean(SpContents.SCHOOLDATA_LOADED, schoolDataLoaded);
    }

    public boolean isSearchDataJsonLoaded() {
        return searchDataJsonLoaded;
    }

    public void setSearchDataJsonLoaded(boolean searchDataJsonLoaded) {
        this.searchDataJsonLoaded = searchDataJsonLoaded;
        spHelper.saveBoolean(SpContents.SCHOOLDATA_JSON_LOADED, searchDataJsonLoaded);
    }

    public boolean isDistrictEstJsonLoaded() {
        return districtEstJsonLoaded;
    }

    public void setDistrictEstJsonLoaded(boolean districtEstJsonLoaded) {
        this.districtEstJsonLoaded = districtEstJsonLoaded;
        spHelper.saveBoolean(SpContents.DISTRICTEST_JSON_LOADED, districtEstJsonLoaded);
    }

    public boolean isgScopeDataJsonLoaded() {
        return gScopeDataJsonLoaded;
    }

    public void setgScopeDataJsonLoaded(boolean gScopeDataJsonLoaded) {
        this.gScopeDataJsonLoaded = gScopeDataJsonLoaded;
        spHelper.saveBoolean(SpContents.GSCOPEDATA_JSON_LOADED, gScopeDataJsonLoaded);
    }

    public boolean isRailLineDataJsonLoaded() {
        return railLineDataJsonLoaded;
    }

    public void setRailLineDataJsonLoaded(boolean railLineDataJsonLoaded) {
        this.railLineDataJsonLoaded = railLineDataJsonLoaded;
        spHelper.saveBoolean(SpContents.RAILLINEDATA_JSON_LOADED, railLineDataJsonLoaded);
    }

    public boolean isStoreDataJsonLoaded() {
        return storeDataJsonLoaded;
    }

    public void setStoreDataJsonLoaded(boolean storeDataJsonLoaded) {
        this.storeDataJsonLoaded = storeDataJsonLoaded;
        spHelper.saveBoolean(SpContents.STOREDATA_JSON_LOADED, storeDataJsonLoaded);
    }

    public boolean isSchoolDataJsonLoaded() {
        return schoolDataJsonLoaded;
    }

    public void setSchoolDataJsonLoaded(boolean schoolDataJsonLoaded) {
        this.schoolDataJsonLoaded = schoolDataJsonLoaded;
        spHelper.saveBoolean(SpContents.SCHOOLDATA_JSON_LOADED, schoolDataJsonLoaded);
    }

    public boolean isNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(boolean networkStatus) {
        this.networkStatus = networkStatus;
    }

    public UserInfoBean getCurrentUserInfo() {
        return currentUserInfo;
    }

    public void setCurrentUserInfo(UserInfoBean currentUserInfo) {
        spHelper.saveUserInfo(currentUserInfo);
        this.currentUserInfo = currentUserInfo;
    }

    public boolean isLogin() {
        if (currentUserInfo != null && !TextUtils.isEmpty(currentUserInfo.getUserId())) {
            return true;
        } else {
            return false;
        }
    }

    public void clearInitDataStatus() {
        searchDataLoaded = false;
        spHelper.saveBoolean(SpContents.SEARCHDATA_LOADED, false);
        districtEstLoaded = false;
        spHelper.saveBoolean(SpContents.DISTRICTEST_LOADED, false);
        gScopeDataLoaded = false;
        spHelper.saveBoolean(SpContents.GSCOPEDATA_LOADED, false);
        railLineDataLoaded = false;
        spHelper.saveBoolean(SpContents.RAILLINEDATA_LOADED, false);
        storeDataLoaded = false;
        spHelper.saveBoolean(SpContents.STOREDATA_LOADED, false);
        schoolDataLoaded = false;
        spHelper.saveBoolean(SpContents.SCHOOLDATA_LOADED, false);

        searchDataJsonLoaded = false;
        spHelper.saveBoolean(SpContents.SEARCHDATA_JSON_LOADED, false);
        districtEstJsonLoaded = false;
        spHelper.saveBoolean(SpContents.DISTRICTEST_JSON_LOADED, false);
        gScopeDataJsonLoaded = false;
        spHelper.saveBoolean(SpContents.GSCOPEDATA_JSON_LOADED, false);
        railLineDataJsonLoaded = false;
        spHelper.saveBoolean(SpContents.RAILLINEDATA_JSON_LOADED, false);
        storeDataJsonLoaded = false;
        spHelper.saveBoolean(SpContents.STOREDATA_JSON_LOADED, false);
        schoolDataJsonLoaded = false;
        spHelper.saveBoolean(SpContents.SCHOOLDATA_JSON_LOADED, false);
    }
}
