package com.android.basic.summary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    //获取当前类的类名
    public String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: current activity is " + TAG);
        setContentView(getResLayoutId());
        ButterKnife.bind(this);
    }

    /**
     * 返回布局ID
     */
    protected abstract int getResLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initViews();

    /**
     * 初始化监听
     */
    protected abstract void initListeners();
}
