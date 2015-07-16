package com.eleks.rnd.nearables;

/**
 * Created by bogdan.melnychuk on 14.07.2015.
 */
public class AuthResponse {
    private String accessToken;
    private String userName;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
