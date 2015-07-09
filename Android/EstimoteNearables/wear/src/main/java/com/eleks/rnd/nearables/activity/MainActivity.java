package com.eleks.rnd.nearables.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;

import com.eleks.rnd.nearables.R;
import com.eleks.rnd.nearables.adapter.PeopleListAdapter;
import com.eleks.rnd.nearables.model.Person;
import com.eleks.rnd.nearables.service.NearablesService;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
    public static final String TAG = "EleksNearables";

    private WearableListView mListView;
    private PeopleListAdapter mAdapter;

    private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        Log.d(TAG, "MainActivity onCreate");

        mAdapter = new PeopleListAdapter(this);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mListView = (WearableListView) stub.findViewById(R.id.listView);
                loader.execute();
                mListView.setAdapter(mAdapter);
                beaconManager = new BeaconManager(MainActivity.this);
                //connectToService();
            }
        });

        Intent intent = new Intent(this, NearablesService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Stopping nearables service...");
        beaconManager.disconnect();
        super.onDestroy();
    }

    private void connectToService() {
        beaconManager.setNearableListener(new BeaconManager.NearableListener() {
            @Override
            public void onNearablesDiscovered(List<Nearable> nearables) {
                Log.d(TAG, "Nearables discovered");
                for (Nearable n : nearables) {
                    Log.d(TAG, "Nearable " + n);
                }
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d(TAG, "Nerables service ready");
                beaconManager.startNearableDiscovery();
            }
        });
    }

    private AsyncTask<Void, Void, Void> loader = new AsyncTask<Void, Void, Void>() {
        private Exception e;
        private List<Person> people;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Type itemsListType = new TypeToken<List<Person>>() {}.getType();
                InputStream is = getAssets().open("input.json");
                people = new Gson().fromJson(new InputStreamReader(is), itemsListType);
            } catch (IOException e) {
                this.e = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (e == null) {
                mAdapter.setData(people);
            }
        }
    };

    private List<Person> getMates() {
        List<Person> result = new ArrayList<>();
        Person me = new Person();
        me.setName("Bogdan Melnychuk");
        me.setLocation("@Kitchen");
        me.setLastActivity(new Date().getTime());

        Person olya = new Person();
        olya.setName("Olha Stadnytska");
        olya.setLocation("@514");
        olya.setLastActivity(new Date().getTime() - 8000 * 60);

        Person iryna = new Person();
        iryna.setName("Iryna Pantel");
        iryna.setLocation("@510");
        iryna.setLastActivity(new Date().getTime());

        Person b = new Person();
        b.setName("Bogdan Shubravyj");
        b.setLocation("@Gym");
        b.setLastActivity(new Date().getTime());

        result.add(me);
        result.add(olya);
        result.add(iryna);
        result.add(b);
        return result;
    }
}
