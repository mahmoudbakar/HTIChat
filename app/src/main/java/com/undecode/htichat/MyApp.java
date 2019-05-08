package com.undecode.htichat;

import android.app.Application;
import android.content.Context;

import com.undecode.htichat.network.Requests;
import com.undecode.htichat.utils.LocaleManager;

public class MyApp extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Requests.getInstance(getContext());
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }
 
    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }
}