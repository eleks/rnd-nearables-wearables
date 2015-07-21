package com.eleks.rnd.nearables.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.eleks.rnd.nearables.model.Person;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import timber.log.Timber;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "eleks.db";
    private static final int DATABASE_VERSION = 1;
    private static final Class<?>[] tables = new Class<?>[]{Person.class};

    private static DatabaseHelper databaseHelper;

    private Map<Class<?>, BaseDaoImpl<?, ?>> daoImpls = new ConcurrentHashMap<>();

    public static DatabaseHelper getHelper() {
        return databaseHelper;
    }

    public static void initHelper(Context context) {
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        databaseHelper = null;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void clearDAO() {
        daoImpls.clear();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            createTables();
        } catch (SQLException e) {
            Timber.e(e, "Error when creating database tables");
            throw new IllegalStateException("Database not created properly");
        }
    }

    private void createTables() throws SQLException {
        for (Class<?> table : tables) {
            TableUtils.createTable(connectionSource, table);
        }
    }

    private void dropTables() throws SQLException {
        for (Class<?> table : tables) {
            TableUtils.dropTable(connectionSource, table, true);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            try {
                dropTables();
                createTables();
            } catch (SQLException e) {
                Timber.e(e, "Error when upgrading database tables");
                throw new IllegalStateException("Database not upgraded properly");
            }
        }
    }

    @Override
    public void close() {
        super.close();
        clearDAO();
    }

    public BaseDaoImpl<?, ?> getDaoInstance(Class<?> clazz) {
        BaseDaoImpl<?, ?> result = daoImpls.get(clazz);
        if (result == null) {
            synchronized (this) {
                if (result == null) {
                    try {
                        Constructor<?> constructor = clazz.getDeclaredConstructor(ConnectionSource.class);
                        result = (BaseDaoImpl<?, ?>) constructor.newInstance(getConnectionSource());
                        daoImpls.put(clazz, result);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return result;
    }
}
