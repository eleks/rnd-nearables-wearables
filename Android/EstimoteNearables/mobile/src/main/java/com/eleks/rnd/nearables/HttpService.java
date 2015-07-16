package com.eleks.rnd.nearables;

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

/**
 * Created by bogdan.melnychuk on 13.07.2015.
 */
public class HttpService {
    private static final String SERVER = "http://ec2-52-28-4-69.eu-central-1.compute.amazonaws.com:8080";
    public static final String AUTH = SERVER + "/authenticate";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String getImageUrl(int empId) {
        return SERVER + "/employee/" + empId + "/photo";
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
