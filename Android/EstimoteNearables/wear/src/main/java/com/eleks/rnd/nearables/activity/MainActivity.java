package com.eleks.rnd.nearables.activity;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;

import com.eleks.rnd.nearables.R;
import com.eleks.rnd.nearables.fragment.MyLocationFragment;
import com.eleks.rnd.nearables.fragment.RecentFragment;
import com.eleks.rnd.nearables.service.NearablesService;

public class MainActivity extends Activity {
    private static final String TAG = "EleksNearables";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Application started");
        setContentView(R.layout.activity_main);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                final GridViewPager mGridPager = (GridViewPager) findViewById(R.id.pager);
                mGridPager.setAdapter(new SampleGridPagerAdapter(MainActivity.this, getFragmentManager()));
            }
        });

        Intent intent = new Intent(this, NearablesService.class);
        startService(intent);
    }

    public class SampleGridPagerAdapter extends FragmentGridPagerAdapter {
        private final Context mContext;

        public SampleGridPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getFragment(int row, int col) {
            if(col == 1) {
                return new RecentFragment();
            } else {
                return new MyLocationFragment();
            }
        }

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount(int row) {
            return 2;
        }
    }
}
