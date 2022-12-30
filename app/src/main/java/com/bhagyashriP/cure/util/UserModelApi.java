package com.bhagyashriP.cure.util;

import android.app.Application;

public class UserModelApi extends Application {

    private String username, userId;
    private static UserModelApi instance;


    public static UserModelApi getInstance(){
        if (instance == null)
            instance = new UserModelApi();

        return instance;
    }

    public UserModelApi() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
