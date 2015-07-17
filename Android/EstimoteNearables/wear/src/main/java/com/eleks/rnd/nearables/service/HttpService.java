package com.eleks.rnd.nearables.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.eleks.rnd.nearables.event.SendingEvent;
import com.eleks.rnd.nearables.model.Movement;
import com.eleks.rnd.nearables.util.PreferencesManager;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.repackaged.okhttp_v2_2_0.com.squareup.okhttp.Callback;
import com.estimote.sdk.repackaged.okhttp_v2_2_0.com.squareup.okhttp.MediaType;
import com.estimote.sdk.repackaged.okhttp_v2_2_0.com.squareup.okhttp.OkHttpClient;
import com.estimote.sdk.repackaged.okhttp_v2_2_0.com.squareup.okhttp.Request;
import com.estimote.sdk.repackaged.okhttp_v2_2_0.com.squareup.okhttp.RequestBody;
import com.estimote.sdk.repackaged.okhttp_v2_2_0.com.squareup.okhttp.Response;
import com.estimote.sdk.repackaged.okio_v1_3_0.okio.BufferedSink;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by bogdan.melnychuk on 13.07.2015.
 */
public class HttpService {
    private static final String SERVER = "http://ec2-52-28-4-69.eu-central-1.compute.amazonaws.com:8080";
    public static final String RECENT = SERVER + "/track/recent";
    public static final String SAVE = SERVER + "/track/save";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String getImageUrl(int empId) {
        return SERVER + "/employee/" + empId + "/photo";
    }

    private static Request.Builder getDefaultRequest(String path, Context context) {
        return new Request.Builder()
                .url(path)
                .addHeader("user_name", PreferencesManager.getUserName(context))
                .addHeader("access_token", PreferencesManager.getAccessToken(context));

    }

    public static List<Movement> getRecentEvents(Context context) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        Request request = getDefaultRequest(RECENT, context).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Type itemsListType = new TypeToken<List<Movement>>() {
        }.getType();
        List<Movement> movements = new Gson().fromJson(response.body().charStream(), itemsListType);
        return movements;
    }

    public static void postNearableData(final Nearable n, Context context) {
        JsonObject jsObj = new JsonObject();
        jsObj.addProperty("timestamp", new Date().getTime());
        jsObj.addProperty("type", "IN_RANGE");
        jsObj.addProperty("stikerId", n.identifier);
        String body = jsObj.toString();
        Log.d("TAG", "Body: " + body);

        final OkHttpClient client = new OkHttpClient();
        RequestBody reqBody = RequestBody.create(JSON, body);
        Request request = getDefaultRequest(SAVE, context).post(reqBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                EventBus.getDefault().post(new SendingEvent(e, n));
            }

            @Override
            public void onResponse(Response response) throws IOException {
                EventBus.getDefault().post(new SendingEvent(n));
            }
        });
        //if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
    }

//    public static Map<String, String> getDefaultHeaders() {
//        Map<String, String> headers = new HashMap<>();
//
//        headers.put("user_name", PreferencesManager.getUserName());
//        headers.put("access_token", PreferencesManager.accessToken());
//
//        return headers;
//    }
//
//    public static InputStream execute(String path, String method, String body, Map<String, String> headers) throws IOException {
//        URL url = new URL(path);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setDoOutput(true);
//        conn.setReadTimeout(5000);
//        conn.setConnectTimeout(5000);
//        conn.setRequestMethod(method);
//        for (Map.Entry<String, String> e : headers.entrySet()) {
//            conn.setRequestProperty(e.getKey(), e.getValue());
//        }
//        conn.connect();
//
//        //Write
//        if (!TextUtils.isEmpty(body)) {
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//            writer.write(body);
//            writer.close();
//            os.close();
//        }
//
//        InputStream in = new BufferedInputStream(conn.getInputStream());
//        return in;
//    }
//
//    public static InputStream executeGet(String path, Map<String, String> headers) throws IOException {
//        return execute(path, "GET", null, headers);
//    }
//
//    public static InputStream executePost(String path, Map<String, String> headers) throws IOException {
//        return execute(path, "POST", null, headers);
//    }
}
