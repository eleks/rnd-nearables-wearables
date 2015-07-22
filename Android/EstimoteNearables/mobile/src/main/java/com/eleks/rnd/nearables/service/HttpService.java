package com.eleks.rnd.nearables.service;

import android.content.Context;

import com.eleks.rnd.nearables.AuthResponse;
import com.eleks.rnd.nearables.PreferencesManager;
import com.eleks.rnd.nearables.model.Movement;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by bogdan.melnychuk on 13.07.2015.
 */
public class HttpService {
    private static final String SERVER = "http://172.25.3.68:8080";
    public static final String AUTH = SERVER + "/authenticate";
    public static final String RECENT = SERVER + "/track/recent";

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

    public static AuthResponse authorize(String userName, String userPass) throws IOException {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder()
                .add("username", userName)
                .add("password", userPass)
                .build();
        Request request = new Request.Builder()
                .url(AUTH)
                .post(formBody).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Type itemsListType = new TypeToken<AuthResponse>() {
        }.getType();
        AuthResponse result = new Gson().fromJson(response.body().charStream(), itemsListType);
        return result;
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
