package com.eleks.rnd.nearables;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import wearprefs.WearPrefs;

/**
 * Created by bogdan.melnychuk on 09.07.2015.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        //WearPrefs.init(this);
    }
}
