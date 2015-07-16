package com.eleks.rnd.nearables.service;

import android.util.Log;

import com.eleks.rnd.nearables.util.PreferencesManager;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by bogdan.melnychuk on 14.07.2015.
 */
public class WearMessageListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("TAG", "onMessageReceived()");
        String message = new String(messageEvent.getData());
        Log.d("TAG", "Message " + message);
        try {
            String[] s = message.split("-");
            PreferencesManager.putUserName(getApplicationContext(), s[0]);
            PreferencesManager.putAccessToken(getApplicationContext(), s[1]);
            NearablesService.recentOnes = null;
        } catch (Exception e) {

        }

    }
}
