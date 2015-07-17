package com.eleks.rnd.nearables.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eleks.rnd.nearables.PreferencesManager;
import com.eleks.rnd.nearables.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mApiClient;

    private static final String START_ACTIVITY = "/logindetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView message = (TextView) findViewById(R.id.message);

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesManager.clear(MainActivity.this);
                Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(myIntent);
                MainActivity.this.finish();
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
        if (mApiClient.isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mApiClient).await();
                    for (Node node : nodes.getNodes()) {
                        String message = PreferencesManager.getUserName(MainActivity.this) + "-" + PreferencesManager.getAccessToken(MainActivity.this);
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
