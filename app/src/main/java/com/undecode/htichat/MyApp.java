package com.undecode.htichat;

import android.app.Application;
import android.content.Context;

import com.undecode.htichat.utils.LocaleManager;

public class MyApp extends Application {
 
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