package com.eleks.rnd.nearables.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.eleks.rnd.nearables.util.PreferencesManager;
import com.eleks.rnd.nearables.R;
import com.eleks.rnd.nearables.fragment.PeopleFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mApiClient;

    public static final String MESSAGE_LOGIN = "/logindetails";
    public static final String MESSAGE_FAVORITE = "/favorites";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        Fragment f = new PeopleFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment, f, PeopleFragment.class.getName()).commit();

        Toast.makeText(this, "Hello " + PreferencesManager.getUserName(this), Toast.LENGTH_SHORT).show();
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

    public void sendMessage(final String path, final String message) {
        if (mApiClient.isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mApiClient).await();
                    for (Node node : nodes.getNodes()) {
                        MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(mApiClient, node.getId(), path, message.getBytes()).await();
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
        String loginMessage = PreferencesManager.getUserName(MainActivity.this) + "-" + PreferencesManager.getAccessToken(MainActivity.this);
        sendMessage(MESSAGE_LOGIN, loginMessage);
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
