package com.eleks.rnd.nearables;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Created by bogdan.melnychuk on 13.07.2015.
 */
public class PreferencesManager {

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

//    publi void putBoolean(String key, Boolean value) {
//        getPreferences(context).edit().putBoolean(key, value).commit();
//    }
//
//    @Override
//    public Boolean getBoolean(String key, Boolean defaultValue) {
//        return getPreferences(context).getBoolean(key, defaultValue);
//    }

    public static void putString(Context context, String key, String value) {
        getPreferences(context).edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getPreferences(context).getString(key, defaultValue);
    }

    public static String getUserName(Context context) {
        return getString(context, "userName", null);
    }

    public static void putUserName(Context context, String value) {
        putString(context, "userName", value);
    }

    public static String getAccessToken(Context context) {
        return getString(context, "accesToken", null);
    }

    public static void putAccessToken(Context context, String value) {
        putString(context, "accesToken", value);
    }

    public static boolean isLoggedIn(Context context) {
        return !TextUtils.isEmpty(getUserName(context)) && !TextUtils.isEmpty(getAccessToken(context));
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.clear().commit();
    }

}
