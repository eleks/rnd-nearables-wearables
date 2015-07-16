package com.eleks.rnd.nearables;

import android.app.Application;

import wearprefs.WearPrefs;

/**
 * Created by bogdan.melnychuk on 14.07.2015.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //WearPrefs.init(this);
    }
}
