package com.undecode.htichat.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.undecode.htichat.MyApp;
import com.undecode.htichat.models.LoginResponse;
import com.undecode.htichat.models.User;

public class MyPreference {
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private Gson gson;

    private final String KEY_TOKEN = "token";
    private final String KEY_LOGIN = "login";
    private final String KEY_USER = "user";

    public MyPreference() {
        preferences = MyApp.getContext().getSharedPreferences("vendor", Context.MODE_PRIVATE);
        gson = new Gson();
        editor = preferences.edit();
        editor.apply();
    }

    public void setToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        return preferences.getString(KEY_TOKEN, "");
    }

    public void login(LoginResponse loginResponse){
        setToken(loginResponse.getSessionID());
        editor.putString(KEY_USER, gson.toJson(loginResponse));
        editor.putBoolean(KEY_LOGIN, true);
        editor.commit();
    }

    public User getMine(){
        return gson.fromJson(preferences.getString(KEY_USER, ""), LoginResponse.class).getUser();
    }

    public boolean isLogin(){
        return preferences.getBoolean(KEY_LOGIN, false);
    }

    public void logout(){
        editor.putBoolean(KEY_LOGIN, false);
        editor.remove(KEY_TOKEN);
        editor.commit();
    }
}
