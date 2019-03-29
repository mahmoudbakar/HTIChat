package com.undecode.htichat.activities;


import com.undecode.htichat.R;

public class ProfileActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initView() {
        showBackArrow();
    }
}
