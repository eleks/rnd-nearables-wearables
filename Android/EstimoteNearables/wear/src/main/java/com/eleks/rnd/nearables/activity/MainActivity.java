package com.eleks.rnd.nearables.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.eleks.rnd.nearables.R;
import com.eleks.rnd.nearables.adapter.PeopleListAdapter;
import com.eleks.rnd.nearables.model.Person;

import java.util.ArrayList;
import java.util.Date;
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

        mAdapter = new PeopleListAdapter(this, getMates());

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mListView = (WearableListView) stub.findViewById(R.id.listView);
                mListView.setAdapter(mAdapter);
            }
        });
    }

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
