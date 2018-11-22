package com.cetnaline.findproperty.model.cache;

import android.content.Context;

import com.cetnaline.findproperty.model.sp.SpContents;
import com.cetnaline.findproperty.model.sp.SpHelper;

public class CacheHolder {

    private int dbVersion; //当前数据库版本
    private boolean isFirstOpen; //首次打开

    /**
     * 初始化数据加载状态
     */
    private boolean searchDataLoaded;
    private boolean districtEstLoaded;
    private boolean gScopeDataLoaded;
    private boolean railLineDataLoaded;
    private boolean storeDataLoaded;
    private boolean schoolDataLoaded;

    private static CacheHolder cacheHolder;
    private static SpHelper spHelper;

    private CacheHolder() {

    }

    public static void init(Context context) {
        if (cacheHolder == null) {
            synchronized (cacheHolder) {
                if (cacheHolder == null) {
                    cacheHolder = new CacheHolder();
                    spHelper = SpHelper.getInstance(context);
                    //加载状态数据
                    cacheHolder.dbVersion = spHelper.getInt(SpContents.DB_VERSION);
                    cacheHolder.searchDataLoaded = spHelper.getBoolean(SpContents.SEARCHDATA_LOADED);
                    cacheHolder.districtEstLoaded = spHelper.getBoolean(SpContents.DISTRICTEST_LOADED);
                    cacheHolder.gScopeDataLoaded = spHelper.getBoolean(SpContents.GSCOPEDATA_LOADED);
                    cacheHolder.railLineDataLoaded = spHelper.getBoolean(SpContents.RAILLINEDATA_LOADED);
                    cacheHolder.storeDataLoaded = spHelper.getBoolean(SpContents.STOREDATA_LOADED);
                    cacheHolder.schoolDataLoaded = spHelper.getBoolean(SpContents.SCHOOLDATA_LOADED);
                    cacheHolder.isFirstOpen = spHelper.getBoolean(SpContents.FIRST_OPEN);
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

    public boolean isFirstOpen() {
        return isFirstOpen;
    }

    public void setFirstOpen(boolean firstOpen) {
        isFirstOpen = firstOpen;
        spHelper.saveBoolean(SpContents.FIRST_OPEN, firstOpen);
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
    }
}
