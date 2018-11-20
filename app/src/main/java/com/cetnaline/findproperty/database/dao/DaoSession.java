package com.cetnaline.findproperty.database.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.cetnaline.findproperty.database.entity.GScope;

import com.cetnaline.findproperty.database.dao.GScopeDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig gScopeDaoConfig;

    private final GScopeDao gScopeDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        gScopeDaoConfig = daoConfigMap.get(GScopeDao.class).clone();
        gScopeDaoConfig.initIdentityScope(type);

        gScopeDao = new GScopeDao(gScopeDaoConfig, this);

        registerDao(GScope.class, gScopeDao);
    }
    
    public void clear() {
        gScopeDaoConfig.clearIdentityScope();
    }

    public GScopeDao getGScopeDao() {
        return gScopeDao;
    }

}