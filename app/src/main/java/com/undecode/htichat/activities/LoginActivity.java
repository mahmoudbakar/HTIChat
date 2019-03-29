package com.undecode.htichat.activities;

import android.content.Intent;
import android.widget.Button;

import com.undecode.htichat.R;

import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edEmail)
    AppCompatEditText edEmail;
    @BindView(R.id.edPassword)
    AppCompatEditText edPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void initView() {
        hideToolbar();
        hideKeyboard();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClicked() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
