package com.undecode.htichat.activities;

import android.content.Intent;
import android.widget.Button;

import com.undecode.htichat.R;
import com.undecode.htichat.models.LoginResponse;
import com.undecode.htichat.models.User;
import com.undecode.htichat.network.API;
import com.undecode.htichat.network.OnResponse;
import com.undecode.htichat.utils.MyPreference;

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
        MyPreference preference = new MyPreference();
        User user = preference.getMine();
        if (user != null){
            if (user.getPhone().length() > 3){
                edEmail.setText(user.getPhone());
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClicked() {
        showProgressDialog();
        API.getInstance().login(edEmail.getText().toString(), edPassword.getText().toString(),
                object -> {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }, this);
    }
}
