package com.eleks.rnd.nearables.database.dao;

import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import timber.log.Timber;

public class BaseDao<T> extends BaseDaoImpl<T, Long> {

    private Class<?> clazz;

    protected BaseDao(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        clazz = dataClass;
    }

    @Override
    public List<T> queryForAll() throws SQLException {
        List<T> result = super.queryForAll();
        if(result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    public void clearAll() throws SQLException {
        TableUtils.clearTable(connectionSource, clazz);
    }

    public AndroidDatabaseResults cursorForAll() throws SQLException {
        return (AndroidDatabaseResults) queryBuilder().iterator().getRawResults();
    }

    public void callInTransaction(Callable<Void> callable) {
        try {
            TransactionManager.callInTransaction(getConnectionSource(), callable);
        } catch (Exception e) {
            Timber.d(e.getMessage(), e);
        }
    }

}
