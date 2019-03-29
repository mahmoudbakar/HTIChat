package com.undecode.htichat.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.undecode.htichat.R;
import com.undecode.htichat.utils.ConnectivityChangeReceiver;
import com.undecode.htichat.utils.LocaleManager;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

import static android.content.pm.PackageManager.GET_META_DATA;

public abstract class BaseActivity extends AppCompatActivity implements ConnectivityChangeReceiver.OnConnectivityChangedListener{

    private ProgressDialog mProgressDialog;
    private ConnectivityChangeReceiver connectivityChangeReceiver;
    private AlertDialog.Builder noConnectionDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        setupNetworkListener();
        resetTitles();
        initView();
    }

    public void setLanguage(String language){

        //setting new configuration
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, null);

        //store current language in prefrence
        //prefData.setCurrentLanguage(language);

        //With new configuration start activity again
        startActivity(new Intent(this, SplashActivity.class));
        finish();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    protected void resetTitles() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                setTitle(info.labelRes);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected abstract void initView();

    protected abstract int getLayout();

    private void setupNetworkListener() {
        connectivityChangeReceiver = new ConnectivityChangeReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectivityChangeReceiver, filter);
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected AlertDialog.Builder showAlertDialog(String msg, String buttonName, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(null);
        dialogBuilder.setIcon(R.mipmap.ic_launcher);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton(buttonName, listener);

        dialogBuilder.setCancelable(false);
        dialogBuilder.show();
        return dialogBuilder;
    }

    protected void showProgressDialog(String title, @NonNull String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            if (title != null)
                mProgressDialog.setTitle(title);
            mProgressDialog.setIcon(R.mipmap.ic_launcher);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    protected void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e);
        }
    }

    protected AlertDialog.Builder noInternetConnectionAvailable() {
        showToast(getString(R.string.message_no_connection));

        return showAlertDialog(getString(R.string.message_no_connection), getString(R.string.button_retry), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    protected void showBackArrow() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    protected void hideToolbar(){
        getSupportActionBar().hide();
    }

    protected void showToolbar(){
        getSupportActionBar().show();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.bind(this);
        unregesterNetworkReciver();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        unregesterNetworkReciver();
        super.onPause();
    }

    @Override
    protected void onStop() {
        unregesterNetworkReciver();
        super.onStop();
    }

    @Override
    protected void onStart() {
        setupNetworkListener();
        super.onStart();
    }

    private void unregesterNetworkReciver(){
        try {
            unregisterReceiver(connectivityChangeReceiver);
        }catch (IllegalArgumentException e){

        }
    }

    @Override
    public void onConnectivityChanged(boolean isConnected) {
        if (isConnected) {
            noConnectionDialog = null;
            hideDialog();
            //showToast("You are online");
        } else {
            noConnectionDialog = noInternetConnectionAvailable();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;
        }
        return true;
    }
}
