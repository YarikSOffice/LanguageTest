package com.yariksoffice.languagetest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yariksoffice.languagetest.R;

public class TestActivity1 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_1);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}