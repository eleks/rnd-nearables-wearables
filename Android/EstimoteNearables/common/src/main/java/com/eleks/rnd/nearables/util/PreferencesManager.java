package com.eleks.rnd.nearables.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.eleks.rnd.nearables.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

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

    public static void putLong(Context context, String key, long value) {
        getPreferences(context).edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return getPreferences(context).getLong(key, defaultValue);
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

    public static void putLastLocation(Context context, String value) {
        putString(context, "lastLocation", value);
    }

    public static String getLastLocation(Context context) {
        return getString(context, "lastLocation", null);
    }

    public static void putLastLocationDate(Context context, long value) {
        putLong(context, "lastLocationDate", value);
    }

    public static long getLastLocationDate(Context context) {
        return getLong(context, "lastLocationDate", 0);
    }

    public static String getFavoritesString(Context context) {
       return getString(context, "favorites", null);
    }

    public static void putFavoritesString(Context context, String value) {
        putString(context, "favorites", value);
    }

    public static Set<Long> getFavorites(Context context) {
        Set<Long> result = new HashSet<>();
        try {
            String favorites = getString(context, "favorites", null);
            if(!TextUtils.isEmpty(favorites)) {
                String[] array = favorites.split("-");

                for (String s : array) {
                    if (!TextUtils.isEmpty(s)) {
                        result.add(Long.valueOf(s));
                    }
                }
            }
        } catch (Exception e) {
            Timber.e(e, e.getMessage());
        }
        return result;
    }

    public static void putFavorites(Collection<Person> list, Context context) {
        StringBuilder sBuilder = new StringBuilder();
        for(Person p : list) {
            sBuilder.append(p.getEmpId());
            sBuilder.append("-");
        }
        putString(context, "favorites", sBuilder.toString());
    }

    public static void putFavoritesIds(Collection<Long> idsList, Context context) {
        StringBuilder sBuilder = new StringBuilder();
        for(Long l : idsList) {
            sBuilder.append(l);
            sBuilder.append("-");
        }
        putString(context, "favorites", sBuilder.toString());
    }


}
