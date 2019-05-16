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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.undecode.htichat.R;
import com.undecode.htichat.network.API;
import com.undecode.htichat.network.OnResponse;
import com.undecode.htichat.utils.ConnectivityChangeReceiver;
import com.undecode.htichat.utils.LocaleManager;
import com.undecode.htichat.utils.MyPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

import static android.content.pm.PackageManager.GET_META_DATA;

public abstract class BaseActivity extends AppCompatActivity implements ConnectivityChangeReceiver.OnConnectivityChangedListener, OnResponse.ErrorResponse {

    private ProgressDialog mProgressDialog;
    private ConnectivityChangeReceiver connectivityChangeReceiver;
    private AlertDialog noConnectionDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        setupNetworkListener();
        resetTitles();
        initView();
    }

    public void setLanguage(String language) {

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
//        connectivityChangeReceiver = new ConnectivityChangeReceiver(this);
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(connectivityChangeReceiver, filter);
    }

    public void showToolbar() {
        if (getSupportActionBar() != null) getSupportActionBar().show();
    }

    public void hideToolbar() {
        if (getSupportActionBar() != null) getSupportActionBar().hide();
    }

    public void showBackArrow() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e);
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void fillSpinner(List list, Spinner spinner) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    /**
     * @param title          dialog title
     * @param msg            message of the alert dialog
     * @param positiveButton button name
     * @param negativeButton button name send "" if you don`t want a negative button
     * @param icon           send 0 if you don`t want an icon
     * @param listener       send null if you don`t want to handle it
     */
    public AlertDialog showAlertDialog(String title, String msg, String positiveButton, String negativeButton, int icon, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialog);
        dialogBuilder.setTitle(title);
        if (icon != 0) {
            dialogBuilder.setIcon(icon);
        }
        dialogBuilder.setMessage(msg);
        if (listener != null) {
            dialogBuilder.setPositiveButton(positiveButton, listener);
            if (!negativeButton.equals(""))
                dialogBuilder.setNegativeButton(negativeButton, listener);
        } else {
            dialogBuilder.setPositiveButton(positiveButton,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        dialogBuilder.setCancelable(false);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.AlertDialog);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage("Loading");
            mProgressDialog.show();
        }
    }

    public void showProgressDialog(String title, @NonNull String message) {
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

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    protected AlertDialog noInternetConnectionAvailable() {
        showToast(getString(R.string.message_no_connection));

        return showAlertDialog(getString(R.string.message_no_connection), getString(R.string.button_retry), "OK", "CANCEL", 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }


    public void openActivity(Class<?> activity, Bundle bundle, boolean finish) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
        if (finish) finish();
    }

    public void goToFragment(Fragment fragment, int frame, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(tag) == null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.replace(frame, fragment).addToBackStack("").commit();
            //ft.add(frame, fragment, tag).addToBackStack(tag).commit();
            ft.replace(frame, fragment, tag).commit();
        }
//      else {
//            if (fragmentManager.findFragmentByTag(tag) == null) {
//                getSupportFragmentManager().popBackStack(tag, 0);
//            }
//        }
        List<Fragment> fragments = fragmentManager.getFragments();
        Log.e("Fragments Quantity", String.valueOf(fragments.size()));
        removeFragments(fragments);
        fragmentManager.beginTransaction().show(fragment).commit();
    }

    private void hideFragments(List<Fragment> fragments) {
        for (Fragment fragment :
                fragments) {
            getSupportFragmentManager().beginTransaction().hide(fragment).commit();
        }
    }

    private void removeFragments(List<Fragment> fragments) {
        for (Fragment fragment :
                fragments) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    public void popFragment() {
        getFragmentManager().popBackStack();
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

    private void unregesterNetworkReciver() {
        try {
            unregisterReceiver(connectivityChangeReceiver);
        } catch (IllegalArgumentException e) {

        }
    }

    @Override
    public void onConnectivityChanged(boolean isConnected) {
        if (isConnected) {
            noConnectionDialog = null;
            hideProgressDialog();
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

    @Override
    public void onNoInternet() {
        hideProgressDialog();
    }

    @Override
    public void onNotAuthorized() {
        hideProgressDialog();
        showToast("Your Session Expired");
        new MyPreference().setToken("");
        openActivity(LoginActivity.class, null, true);
    }

    @Override
    public void onNotAllowedMethod() {
        hideProgressDialog();
    }

    @Override
    public void onApiNotFound() {
        hideProgressDialog();
    }

    @Override
    public void onBadRequest(JSONObject object) {
        hideProgressDialog();
    }

    @Override
    public void onServerSideError() {
        hideProgressDialog();
    }

    @Override
    public void onForbidden() {
        hideProgressDialog();
    }
}
