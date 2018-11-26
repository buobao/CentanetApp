package com.cetnaline.findproperty.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cetnaline.findproperty.model.database.dao.DaoMaster;
import com.cetnaline.findproperty.model.database.dao.DaoSession;
import com.cetnaline.findproperty.model.database.dao.DropMenuDao;
import com.cetnaline.findproperty.model.database.dao.GScopeDao;
import com.cetnaline.findproperty.model.database.dao.RailWayDao;
import com.cetnaline.findproperty.model.database.entity.DropMenu;
import com.cetnaline.findproperty.model.database.entity.GScope;
import com.cetnaline.findproperty.model.database.entity.RailLine;
import com.cetnaline.findproperty.model.database.entity.RailWay;
import com.cetnaline.findproperty.model.database.entity.School;
import com.cetnaline.findproperty.model.database.entity.Store;
import com.cetnaline.findproperty.model.network.bean.responsebean.SearchMenuBean;

import java.util.ArrayList;
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

    public static void init(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
    }

    public static DatabaseHelper getInstance() {
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
        if (gScopes == null || gScopes.size() <= 0) {
            return;
        }
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

    public void insertRailLines(List<RailLine> railLines) {
        if (railLines == null || railLines.size() == 0) {
            return;
        }
        writeDaoSession.getRailLineDao().insertOrReplaceInTx(railLines);
        List<RailWay> allRailWay = new ArrayList<>();
        for (RailLine railLine : railLines) {
            List<RailWay> railWays = railLine.getRailWayList();
            if (railWays != null) {
                for (RailWay railWay : railWays) {
                    railWay.setRailLineID(railLine.getRailLineID());
                }
                allRailWay.addAll(railWays);
            }
        }
        writeDaoSession.getRailWayDao().insertOrReplaceInTx(allRailWay);
    }

    public List<RailLine> getRailLines() {
        return readDaoSession.getRailLineDao().queryBuilder().list();
    }

    public List<RailWay> getRailWayByRailLineId(String radilLineId) {
        return readDaoSession.getRailWayDao().queryBuilder()
                .where(RailWayDao.Properties.RailLineID.eq(radilLineId))
                .list();
    }

    public void insertSchools(List<School> schools) {
        if (schools == null || schools.size() <= 0) {
            return;
        }
        writeDaoSession.getSchoolDao().insertOrReplaceInTx(schools);
    }

    public List<School> getSchools() {
        return readDaoSession.getSchoolDao().queryBuilder().list();
    }

    public void inseartSearchDatas(List<SearchMenuBean> searchMenuBeans) {
        if (searchMenuBeans == null || searchMenuBeans.size() <= 0) {
            return;
        }
        List<DropMenu> allDrops = new ArrayList<>();
        for (SearchMenuBean searchData : searchMenuBeans) {
            List<DropMenu> dropBos = searchData.getSearchDataItemList();
            for (DropMenu dropBo : dropBos) {
                dropBo.setName(searchData.getName());
            }
            allDrops.addAll(dropBos);
        }
        writeDaoSession.getDropMenuDao().insertInTx(allDrops);
    }

    public List<DropMenu> getSearchDataByName(String name) {
        return readDaoSession.getDropMenuDao().queryBuilder()
                .where(DropMenuDao.Properties.Name.eq(name))
                .list();
    }

    public void insertStores(List<Store> stores) {
        if (stores == null || stores.size() <= 0) {
            return;
        }
        writeDaoSession.getStoreDao().insertOrReplaceInTx(stores);
    }

    public List<Store> getStores() {
        return readDaoSession.getStoreDao().queryBuilder().list();
    }
}


























