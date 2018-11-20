package com.cetnaline.findproperty.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.cetnaline.findproperty.database.entity.GScope;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "centanet_gscope".
*/
public class GScopeDao extends AbstractDao<GScope, Integer> {

    public static final String TABLENAME = "centanet_gscope";

    /**
     * Properties of entity GScope.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property GScopeId = new Property(0, Integer.class, "GScopeId", true, "GSCOPE_ID");
        public final static Property GScopeCode = new Property(1, String.class, "GScopeCode", false, "GSCOPE_CODE");
        public final static Property GScopeLevel = new Property(2, Integer.class, "GScopeLevel", false, "GSCOPE_LEVEL");
        public final static Property GScopeName = new Property(3, String.class, "GScopeName", false, "GSCOPE_NAME");
        public final static Property GScopeAlias = new Property(4, String.class, "GScopeAlias", false, "GSCOPE_ALIAS");
        public final static Property FullPY = new Property(5, String.class, "FullPY", false, "FULL_PY");
        public final static Property FirstPY = new Property(6, String.class, "FirstPY", false, "FIRST_PY");
        public final static Property ParentId = new Property(7, Integer.class, "ParentId", false, "PARENT_ID");
        public final static Property OrderBy = new Property(8, Integer.class, "OrderBy", false, "ORDER_BY");
        public final static Property Lng = new Property(9, Double.class, "Lng", false, "LNG");
        public final static Property Lat = new Property(10, Double.class, "Lat", false, "LAT");
        public final static Property SaleCount = new Property(11, Integer.class, "SaleCount", false, "SALE_COUNT");
        public final static Property RentCount = new Property(12, Integer.class, "RentCount", false, "RENT_COUNT");
        public final static Property EstateCount = new Property(13, Integer.class, "EstateCount", false, "ESTATE_COUNT");
        public final static Property SaleAvgPrice = new Property(14, Double.class, "SaleAvgPrice", false, "SALE_AVG_PRICE");
        public final static Property SaleAvgPriceRise = new Property(15, Double.class, "SaleAvgPriceRise", false, "SALE_AVG_PRICE_RISE");
        public final static Property PostCount = new Property(16, Integer.class, "PostCount", false, "POST_COUNT");
    }


    public GScopeDao(DaoConfig config) {
        super(config);
    }
    
    public GScopeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"centanet_gscope\" (" + //
                "\"GSCOPE_ID\" INTEGER PRIMARY KEY ," + // 0: GScopeId
                "\"GSCOPE_CODE\" TEXT," + // 1: GScopeCode
                "\"GSCOPE_LEVEL\" INTEGER," + // 2: GScopeLevel
                "\"GSCOPE_NAME\" TEXT," + // 3: GScopeName
                "\"GSCOPE_ALIAS\" TEXT," + // 4: GScopeAlias
                "\"FULL_PY\" TEXT," + // 5: FullPY
                "\"FIRST_PY\" TEXT," + // 6: FirstPY
                "\"PARENT_ID\" INTEGER," + // 7: ParentId
                "\"ORDER_BY\" INTEGER," + // 8: OrderBy
                "\"LNG\" REAL," + // 9: Lng
                "\"LAT\" REAL," + // 10: Lat
                "\"SALE_COUNT\" INTEGER," + // 11: SaleCount
                "\"RENT_COUNT\" INTEGER," + // 12: RentCount
                "\"ESTATE_COUNT\" INTEGER," + // 13: EstateCount
                "\"SALE_AVG_PRICE\" REAL," + // 14: SaleAvgPrice
                "\"SALE_AVG_PRICE_RISE\" REAL," + // 15: SaleAvgPriceRise
                "\"POST_COUNT\" INTEGER);"); // 16: PostCount
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"centanet_gscope\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GScope entity) {
        stmt.clearBindings();
 
        Integer GScopeId = entity.getGScopeId();
        if (GScopeId != null) {
            stmt.bindLong(1, GScopeId);
        }
 
        String GScopeCode = entity.getGScopeCode();
        if (GScopeCode != null) {
            stmt.bindString(2, GScopeCode);
        }
 
        Integer GScopeLevel = entity.getGScopeLevel();
        if (GScopeLevel != null) {
            stmt.bindLong(3, GScopeLevel);
        }
 
        String GScopeName = entity.getGScopeName();
        if (GScopeName != null) {
            stmt.bindString(4, GScopeName);
        }
 
        String GScopeAlias = entity.getGScopeAlias();
        if (GScopeAlias != null) {
            stmt.bindString(5, GScopeAlias);
        }
 
        String FullPY = entity.getFullPY();
        if (FullPY != null) {
            stmt.bindString(6, FullPY);
        }
 
        String FirstPY = entity.getFirstPY();
        if (FirstPY != null) {
            stmt.bindString(7, FirstPY);
        }
 
        Integer ParentId = entity.getParentId();
        if (ParentId != null) {
            stmt.bindLong(8, ParentId);
        }
 
        Integer OrderBy = entity.getOrderBy();
        if (OrderBy != null) {
            stmt.bindLong(9, OrderBy);
        }
 
        Double Lng = entity.getLng();
        if (Lng != null) {
            stmt.bindDouble(10, Lng);
        }
 
        Double Lat = entity.getLat();
        if (Lat != null) {
            stmt.bindDouble(11, Lat);
        }
 
        Integer SaleCount = entity.getSaleCount();
        if (SaleCount != null) {
            stmt.bindLong(12, SaleCount);
        }
 
        Integer RentCount = entity.getRentCount();
        if (RentCount != null) {
            stmt.bindLong(13, RentCount);
        }
 
        Integer EstateCount = entity.getEstateCount();
        if (EstateCount != null) {
            stmt.bindLong(14, EstateCount);
        }
 
        Double SaleAvgPrice = entity.getSaleAvgPrice();
        if (SaleAvgPrice != null) {
            stmt.bindDouble(15, SaleAvgPrice);
        }
 
        Double SaleAvgPriceRise = entity.getSaleAvgPriceRise();
        if (SaleAvgPriceRise != null) {
            stmt.bindDouble(16, SaleAvgPriceRise);
        }
 
        Integer PostCount = entity.getPostCount();
        if (PostCount != null) {
            stmt.bindLong(17, PostCount);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GScope entity) {
        stmt.clearBindings();
 
        Integer GScopeId = entity.getGScopeId();
        if (GScopeId != null) {
            stmt.bindLong(1, GScopeId);
        }
 
        String GScopeCode = entity.getGScopeCode();
        if (GScopeCode != null) {
            stmt.bindString(2, GScopeCode);
        }
 
        Integer GScopeLevel = entity.getGScopeLevel();
        if (GScopeLevel != null) {
            stmt.bindLong(3, GScopeLevel);
        }
 
        String GScopeName = entity.getGScopeName();
        if (GScopeName != null) {
            stmt.bindString(4, GScopeName);
        }
 
        String GScopeAlias = entity.getGScopeAlias();
        if (GScopeAlias != null) {
            stmt.bindString(5, GScopeAlias);
        }
 
        String FullPY = entity.getFullPY();
        if (FullPY != null) {
            stmt.bindString(6, FullPY);
        }
 
        String FirstPY = entity.getFirstPY();
        if (FirstPY != null) {
            stmt.bindString(7, FirstPY);
        }
 
        Integer ParentId = entity.getParentId();
        if (ParentId != null) {
            stmt.bindLong(8, ParentId);
        }
 
        Integer OrderBy = entity.getOrderBy();
        if (OrderBy != null) {
            stmt.bindLong(9, OrderBy);
        }
 
        Double Lng = entity.getLng();
        if (Lng != null) {
            stmt.bindDouble(10, Lng);
        }
 
        Double Lat = entity.getLat();
        if (Lat != null) {
            stmt.bindDouble(11, Lat);
        }
 
        Integer SaleCount = entity.getSaleCount();
        if (SaleCount != null) {
            stmt.bindLong(12, SaleCount);
        }
 
        Integer RentCount = entity.getRentCount();
        if (RentCount != null) {
            stmt.bindLong(13, RentCount);
        }
 
        Integer EstateCount = entity.getEstateCount();
        if (EstateCount != null) {
            stmt.bindLong(14, EstateCount);
        }
 
        Double SaleAvgPrice = entity.getSaleAvgPrice();
        if (SaleAvgPrice != null) {
            stmt.bindDouble(15, SaleAvgPrice);
        }
 
        Double SaleAvgPriceRise = entity.getSaleAvgPriceRise();
        if (SaleAvgPriceRise != null) {
            stmt.bindDouble(16, SaleAvgPriceRise);
        }
 
        Integer PostCount = entity.getPostCount();
        if (PostCount != null) {
            stmt.bindLong(17, PostCount);
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }    

    @Override
    public GScope readEntity(Cursor cursor, int offset) {
        GScope entity = new GScope( //
            cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // GScopeId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // GScopeCode
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // GScopeLevel
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // GScopeName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // GScopeAlias
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // FullPY
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // FirstPY
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // ParentId
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // OrderBy
            cursor.isNull(offset + 9) ? null : cursor.getDouble(offset + 9), // Lng
            cursor.isNull(offset + 10) ? null : cursor.getDouble(offset + 10), // Lat
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11), // SaleCount
            cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12), // RentCount
            cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // EstateCount
            cursor.isNull(offset + 14) ? null : cursor.getDouble(offset + 14), // SaleAvgPrice
            cursor.isNull(offset + 15) ? null : cursor.getDouble(offset + 15), // SaleAvgPriceRise
            cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16) // PostCount
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GScope entity, int offset) {
        entity.setGScopeId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setGScopeCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGScopeLevel(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setGScopeName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setGScopeAlias(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setFullPY(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setFirstPY(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setParentId(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setOrderBy(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setLng(cursor.isNull(offset + 9) ? null : cursor.getDouble(offset + 9));
        entity.setLat(cursor.isNull(offset + 10) ? null : cursor.getDouble(offset + 10));
        entity.setSaleCount(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
        entity.setRentCount(cursor.isNull(offset + 12) ? null : cursor.getInt(offset + 12));
        entity.setEstateCount(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setSaleAvgPrice(cursor.isNull(offset + 14) ? null : cursor.getDouble(offset + 14));
        entity.setSaleAvgPriceRise(cursor.isNull(offset + 15) ? null : cursor.getDouble(offset + 15));
        entity.setPostCount(cursor.isNull(offset + 16) ? null : cursor.getInt(offset + 16));
     }
    
    @Override
    protected final Integer updateKeyAfterInsert(GScope entity, long rowId) {
        return entity.getGScopeId();
    }
    
    @Override
    public Integer getKey(GScope entity) {
        if(entity != null) {
            return entity.getGScopeId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(GScope entity) {
        return entity.getGScopeId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
