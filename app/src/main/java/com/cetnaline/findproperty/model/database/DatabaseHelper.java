package com.cetnaline.findproperty.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cetnaline.findproperty.model.database.dao.DaoMaster;
import com.cetnaline.findproperty.model.database.dao.DaoSession;
import com.cetnaline.findproperty.model.database.dao.GScopeDao;
import com.cetnaline.findproperty.model.database.entity.GScope;

import java.util.List;

public class DatabaseHelper {

    private DaoMaster.DevOpenHelper openHelper;
    private DaoSession writeDaoSession;
    private DaoSession readDaoSession;
    private static DatabaseHelper databaseHelper;
    private int dbVersion;

    private DatabaseHelper(Context context) {
        openHelper = new DaoMaster.DevOpenHelper(context, "findproperty-db", null);
        DaoMaster writeDaoMaster = new DaoMaster(getWritableDatabase());
        writeDaoSession = writeDaoMaster.newSession();

        DaoMaster readDaoMaster = new DaoMaster(getReadableDatabase());
        dbVersion = readDaoMaster.getSchemaVersion();
        readDaoSession = readDaoMaster.newSession();
    }

    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            throw new RuntimeException("DbManager not instance");
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            throw new RuntimeException("DbManager not instance");
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (databaseHelper == null) {
                    databaseHelper = new DatabaseHelper(context);
                }
            }
        }
        return databaseHelper;
    }

    /**
     * 当前数据库版本号
     * @return
     */
    public int getDbVersion() {
        return dbVersion;
    }

    /**
     * 插入gscope数据
     * @param gScopes
     * @return 插入成功标识
     */
    public void insertGscopes(List<GScope> gScopes){
        writeDaoSession.getGScopeDao().insertOrReplaceInTx(gScopes);
    }

    /**
     * 依据id查询gscope
     * @param id
     * @return
     */
    public GScope getGscopes(int id) {
        GScope gScope = readDaoSession.getGScopeDao().queryBuilder()
                .where(GScopeDao.Properties.GScopeId.eq(id))
                .unique();
        return gScope;
    }

    /**
     * 依据父节点查询子节点
     * @param parentId
     * @return
     */
    public List<GScope> getChildGscopes(int parentId) {
        List<GScope> gScopes = readDaoSession.getGScopeDao().queryBuilder()
                .where(GScopeDao.Properties.ParentId.eq(parentId))
                .list();
        return gScopes;
    }


}


























