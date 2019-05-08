package com.undecode.htichat.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.undecode.htichat.activities.BaseActivity;
import com.undecode.htichat.network.OnResponse;

import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements OnResponse.ErrorResponse {


    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(isHasOptionsMenu());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        setTitle(getTitle());
        initView(view);
        return view;
    }

    protected boolean isHasOptionsMenu() {
        return false;
    }

    protected abstract int getLayout();

    protected abstract String getTitle();

    public void setTitle(String title) {
        getBaseActivity().setTitle(title);
    }

    protected abstract void initView(View view);

    public void showToolbar() {
        getBaseActivity().showToolbar();
    }

    public void hideToolbar() {
        getBaseActivity().hideToolbar();
    }

    public void showBackArrow() {
        getBaseActivity().showBackArrow();
    }

    public void hideKeyboard() {
        getBaseActivity().hideKeyboard();
    }

    public void showToast(String message) {
        getBaseActivity().showToast(message);
    }

    public void fillSpinner(List list, Spinner spinner) {
        getBaseActivity().fillSpinner(list, spinner);
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
        return getBaseActivity().showAlertDialog(title, msg, positiveButton, negativeButton, icon, listener);
    }

    public void showProgressDialog() {
        getBaseActivity().showProgressDialog();
    }

    public void showProgressDialog(String title, @NonNull String message) {
        getBaseActivity().showProgressDialog(title, message);
    }

    public void hideProgressDialog() {
        getBaseActivity().hideProgressDialog();
    }

    public void openActivity(Class<?> activity, Bundle bundle, boolean finish) {
        getBaseActivity().openActivity(activity, bundle, finish);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) setTitle(getTitle());
    }

    public BaseActivity getBaseActivity() {
        return ((BaseActivity) getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onNoInternet() {
        getBaseActivity().onNoInternet();
    }

    @Override
    public void onNotAuthorized() {
        getBaseActivity().onNotAuthorized();
    }

    @Override
    public void onNotAllowedMethod() {
        getBaseActivity().onNotAllowedMethod();
    }

    @Override
    public void onApiNotFound() {
        getBaseActivity().onApiNotFound();
    }

    @Override
    public void onBadRequest(JSONObject object) {
        getBaseActivity().onBadRequest(object);
    }

    @Override
    public void onServerSideError() {
        getBaseActivity().onServerSideError();
    }

    @Override
    public void onForbidden() {
        getBaseActivity().onForbidden();
    }
}
