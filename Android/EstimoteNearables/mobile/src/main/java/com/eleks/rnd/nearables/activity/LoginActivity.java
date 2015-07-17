package com.eleks.rnd.nearables.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eleks.rnd.nearables.AuthResponse;
import com.eleks.rnd.nearables.service.HttpService;
import com.eleks.rnd.nearables.PreferencesManager;
import com.eleks.rnd.nearables.R;

import java.io.IOException;


public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private Button signIn;

    private class LoginAsync extends AsyncTask<Void, Void, Void> {
        private Exception e;
        private AuthResponse response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            signIn.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("TAG", "EXECUTING AUTH");
                response = HttpService.authorize(login.getText().toString(), password.getText().toString());
                Log.d("TAG", "RESULT " + response.toString());
            } catch (IOException e) {
                this.e = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (e == null) {
                PreferencesManager.putAccessToken(LoginActivity.this, response.getAccessToken());
                PreferencesManager.putUserName(LoginActivity.this, response.getUserName());
                goToDashboars();
            } else {
                Toast.makeText(LoginActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            signIn.setEnabled(true);
        }
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (PreferencesManager.isLoggedIn(this)) {
            goToDashboars();
        }

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.signin);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAsync async = new LoginAsync();
                async.execute();
            }
        });
    }

    private void goToDashboars() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
