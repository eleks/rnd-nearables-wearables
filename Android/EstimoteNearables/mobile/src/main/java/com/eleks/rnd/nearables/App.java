package com.eleks.rnd.nearables;

import android.app.Application;

import com.eleks.rnd.nearables.database.DatabaseHelper;

import timber.log.Timber;


/**
 * Created by bogdan.melnychuk on 14.07.2015.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        DatabaseHelper.initHelper(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DatabaseHelper.releaseHelper();
    }
}
