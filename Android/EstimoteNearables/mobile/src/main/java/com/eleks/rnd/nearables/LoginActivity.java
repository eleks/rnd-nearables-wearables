package com.eleks.rnd.nearables;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.TimeUnit;

import wearprefs.WearPrefs;


public class LoginActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mApiClient;

    private static final String START_ACTIVITY = "/logindetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView message = (TextView) findViewById(R.id.message);

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesManager.clear(LoginActivity.this);
                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(myIntent);
                LoginActivity.this.finish();
            }
        });

        message.setText("Hello " + PreferencesManager.getUserName(this));
        initGoogleApiClient();
    }

    private void initGoogleApiClient() {
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mApiClient.disconnect();
        } catch (Exception e) {

        }
    }

    private void sendMessage(final String path) {
//        if (mApiClient.isConnected()) {
//            Log.d("TAG", "sending message");
//            //create the dataMapRequest with a path from constants.Path must start with a /
//            PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(path);
//            putDataMapRequest.getDataMap().putString("userName", "title");
//            putDataMapRequest.getDataMap().putString("accessToken", "some other string data");
//            PutDataRequest request = putDataMapRequest.asPutDataRequest();
//            Wearable.DataApi.putDataItem(mApiClient, request).setResultCallback(new ResultCallback() {
//                @Override
//                public void onResult(Result result) {
//                    if (!result.getStatus().isSuccess()) {
//                        Log.d("TAG", "onResult success");
//                    } else {
//                        Log.d("TAG", "onResult fail");
//                    }
//                }
//            });
//        }

        if (mApiClient.isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mApiClient).await();
                    for (Node node : nodes.getNodes()) {
                        String message = PreferencesManager.getUserName(LoginActivity.this) + "-" + PreferencesManager.getAccessToken(LoginActivity.this);
                        MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(mApiClient, node.getId(), START_ACTIVITY, message.getBytes()).await();
                        if (!result.getStatus().isSuccess()) {
                            Log.e("TAG", "error");
                        } else {
                            Log.i("TAG", "success!! sent to: " + node.getDisplayName());
                        }
                    }
                }
            }).start();

        } else {
            Log.e("test", "not connected");
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("TAG", "onConnected");
        sendMessage(START_ACTIVITY);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("TAG", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult onConnectionFailed) {
        Log.d("TAG", "onConnectionFailed " + onConnectionFailed);
    }
}
