package com.undecode.htichat.activities;


import android.content.Intent;

import com.undecode.htichat.R;
import com.undecode.htichat.utils.MyPreference;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {


    @Override
    protected void initView() {
        hideToolbar();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (new MyPreference().isLogin()){
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                finish();
            }
        }, 5000);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }
}
