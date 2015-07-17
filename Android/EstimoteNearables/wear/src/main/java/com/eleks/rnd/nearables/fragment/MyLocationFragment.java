package com.eleks.rnd.nearables.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleks.rnd.nearables.R;
import com.eleks.rnd.nearables.Stickers;
import com.eleks.rnd.nearables.event.NearableDiscoveredEvent;
import com.eleks.rnd.nearables.event.SendingEvent;
import com.estimote.sdk.Nearable;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by bogdan.melnychuk on 16.07.2015.
 */
public class MyLocationFragment extends Fragment {

    private TextView location;
    private TextView sendingStatus;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_location, container, false);
        location = (TextView) rootView.findViewById(R.id.lastNearable);
        sendingStatus = (TextView) rootView.findViewById(R.id.sendingStatus);

        //fillStickerDetails(PreferencesManager.getLastLocation(getActivity()), PreferencesManager.getLastLocationDate(getActivity()), 0);

        return rootView;
    }

    private void fillStickerDetails(List<Nearable> stickerIds) {
        StringBuilder stickers = new StringBuilder();

        for(Nearable n : stickerIds) {
            stickers.append(Stickers.getStickerName(n.identifier));
            stickers.append("\n");
        }

        //if (lastTime > 0) {
        //    String dateDetails = DateUtils.getDateDiffMessage(new Date(lastTime));
        //    lastStickerDetails += dateDetails;
        //}

        location.setText(stickers.toString());
    }

    public void onEventMainThread(NearableDiscoveredEvent event) {
        fillStickerDetails(event.getNearables());
    }

    public void onEventMainThread(SendingEvent event) {
        String name = Stickers.getStickerName(event.getNearable().identifier);
        sendingStatus.setText(event.wasSuccessful() ? name + " has been sent" : name + " sending error");
    }
}
