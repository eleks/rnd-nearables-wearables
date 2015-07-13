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
import com.eleks.rnd.nearables.model.Movement;
import com.eleks.rnd.nearables.service.HttpService;
import com.eleks.rnd.nearables.service.NearablesService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends Activity {
    public static final String TAG = "EleksNearables";

    private WearableListView mListView;
    private PeopleListAdapter mAdapter;

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
            }
        });

        Intent intent = new Intent(this, NearablesService.class);
        startService(intent);
    }

    private AsyncTask<Void, Void, Void> loader = new AsyncTask<Void, Void, Void>() {
        private Exception e;
        private List<Movement> movements;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                movements = HttpService.getRecentEvents();
            } catch (IOException e) {
                this.e = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (e == null) {
                mAdapter.setData(movements);
            }
        }
    };
}
