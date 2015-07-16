package com.eleks.rnd.nearables.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.eleks.rnd.nearables.util.PreferencesManager;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.Region;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Bogdan.Melnychuk on 25.06.2015.
 */
public class NearablesService extends Service {

    private BeaconManager beaconManager;
    public static List<Nearable> recentOnes;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = new BeaconManager(this);
    }

    @Override
    public void onDestroy() {
        beaconManager.disconnect();
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "On Start Command");
        connectToService();
        return START_STICKY;
    }

    private void connectToService() {
        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override
            public void onNearablesDiscovered(List<Nearable> nearables) {
                //Log.d("TAG", "Discovered " + (nearables == null ? "0" : "" + nearables.size()));
                if (nearables != null) {
                    for (Nearable n : nearables) {
                        if (recentOnes == null || !recentOnes.contains(n)) {
                            Log.d("TAG", "User " + PreferencesManager.getUserName(getApplicationContext()));
                            Log.d("TAG", "Sending details " + n.identifier);
                            sendNearablesDetails(n);
                        }
                    }
                }
                recentOnes = nearables;
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d("TAG", "Service ready");
                beaconManager.startNearableDiscovery();
            }
        });
    }

    private void sendNearablesDetails(final Nearable n) {
        //"timestamp": 12223,
        //"type": "IN_RANGE",
        //"stikerId": "7161b96ea10bd9eb6egfg929e1aa09d5230"
        HttpService.postNearableData(n, getApplicationContext());

    }

    private boolean justDicovered(Nearable n) {
        return recentOnes != null && !recentOnes.contains(n);
    }

    private List<Nearable> getOutRanged(List<Nearable> discovered) {
        if (discovered == null) {
            return recentOnes;
        }

        List<Nearable> result = new ArrayList<>();
        if (recentOnes != null) {
            for (Nearable n : recentOnes) {
                if (!discovered.contains(n)) {
                    result.add(n);
                }
            }
        }
        return result;
    }

}
