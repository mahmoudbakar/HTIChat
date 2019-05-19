package com.undecode.htichat.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatEditText;

import com.undecode.htichat.R;
import com.undecode.htichat.models.RegistrationRequest;
import com.undecode.htichat.network.API;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends BaseActivity {

    @BindView(R.id.edName)
    AppCompatEditText edName;
    @BindView(R.id.edEmail)
    AppCompatEditText edEmail;
    @BindView(R.id.edPhone)
    AppCompatEditText edPhone;
    @BindView(R.id.edPassword)
    AppCompatEditText edPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    RegistrationRequest registrationRequest;

    @Override
    protected void initView() {
        registrationRequest = new RegistrationRequest();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_signup;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        showProgressDialog();
        registrationRequest.setName(edName.getText().toString());
        registrationRequest.setEmail(edEmail.getText().toString());
        registrationRequest.setPhone(edPhone.getText().toString());
        registrationRequest.setPassword(edPassword.getText().toString());
        registrationRequest.setDateBirth("1991-01-01");
        registrationRequest.setGender("1");
        API.getInstance().register(registrationRequest, object -> {
            showToast("Account Registered");
            finish();
            hideProgressDialog();
        }, this);
    }
}
