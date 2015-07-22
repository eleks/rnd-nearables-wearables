package com.eleks.rnd.nearables;

import android.app.Application;

import com.eleks.rnd.nearables.database.DatabaseHelper;
import com.github.johnkil.print.PrintConfig;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
        PrintConfig.initDefault(getAssets(), "fonts/material-icon-font.ttf");
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DatabaseHelper.releaseHelper();
    }
}
