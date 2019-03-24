package com.undecode.htichat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btnShowToast)
    Button btnShowToast;
    @BindView(R.id.showAlertDialog)
    Button showAlertDialog;
    @BindView(R.id.btnHideKeyboard)
    Button btnHideKeyboard;
    @BindView(R.id.btnNoInternet)
    Button btnNoInternet;
    @BindView(R.id.btnShowBackArrow)
    Button btnShowBackArrow;
    @BindView(R.id.btnOpenChat)
    Button btnOpenChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btnShowToast)
    public void onBtnShowToastClicked() {
        showToast("Hello All This is Test Base Toast :D");
    }

    @OnClick(R.id.showAlertDialog)
    public void onShowAlertDialogClicked() {
        showAlertDialog("This is test alert dialog", getString(R.string.button_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    @OnClick(R.id.btnHideKeyboard)
    public void onBtnHideKeyboardClicked() {
        hideKeyboard();
    }

    @OnClick(R.id.btnNoInternet)
    public void onBtnNoInternetClicked() {
        noInternetConnectionAvailable();
    }

    @OnClick(R.id.btnShowBackArrow)
    public void onBtnShowBackArrowClicked() {
        showBackArrow();
    }

    @OnClick(R.id.btnOpenChat)
    public void onOpenChatClicked() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
}
