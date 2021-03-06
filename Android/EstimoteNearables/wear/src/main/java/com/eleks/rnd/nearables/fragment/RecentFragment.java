package com.eleks.rnd.nearables.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleks.rnd.nearables.R;
import com.eleks.rnd.nearables.adapter.PeopleListAdapter;
import com.eleks.rnd.nearables.model.Movement;
import com.eleks.rnd.nearables.service.HttpService;
import com.eleks.rnd.nearables.util.PreferencesManager;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by bogdan.melnychuk on 16.07.2015.
 */
public class RecentFragment extends Fragment {
    public static final String TAG = "TAG";

    private WearableListView mListView;
    private PeopleListAdapter mAdapter;

    private class RecentAsync extends AsyncTask<Void, Void, Void> {
        private Exception e;
        private List<Movement> movements;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                movements = HttpService.getRecentEvents(getActivity());
                Set<Long> favorites = PreferencesManager.getFavorites(getActivity());
                Iterator<Movement> iter = movements.iterator();
                while(iter.hasNext()) {
                    Movement m = iter.next();
                    if(!favorites.contains(Long.valueOf(m.getEmployeeId()))) {
                        iter.remove();
                    }
                }
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recent, container, false);


        mAdapter = new PeopleListAdapter(getActivity());
        mListView = (WearableListView) rootView.findViewById(R.id.listView);
        mListView.setGreedyTouchMode(true);
        mListView.setAdapter(mAdapter);

        mListView.setOverScrollListener(new WearableListView.OnOverScrollListener() {
            @Override
            public void onOverScroll() {
                new RecentAsync().execute();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new RecentAsync().execute();
    }
}
