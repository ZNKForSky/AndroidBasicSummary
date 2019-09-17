package com.android.basic.summary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.basic.summary.activity.AnrActivity;
import com.android.basic.summary.activity.BaseActivity;
import com.android.basic.summary.activity.MyServiceActivity;
import com.android.basic.summary.activity.ZXingActivity;
import com.android.basic.summary.activity.ui.ProgressbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.lv_main)
    ListView mListView;
    private String[] views = {"Activity", "Service", "Broadcast Receiver", "Content Provider", "ANR"};
    private ArrayAdapter<String> arrayAdapter;
    private final int FLAG_JUMP_TO_ACTIVITY = 0;
    private final int FLAG_JUMP_TO_SERVICE = 1;
    private final int FLAG_JUMP_TO_BROADCAST_RECEIVER = 2;
    private final int FLAG_JUMP_TO_CONTENT_PROVIDER = 3;
    private final int FLAG_JUMP_TO_ANR = 4;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initViews();
        initListeners();
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, views);
        mListView.setAdapter(arrayAdapter);
    }

    @Override
    protected void initListeners() {
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (intent == null) {
            intent = new Intent();
        }
        switch (position) {

            case FLAG_JUMP_TO_ACTIVITY://跳转至活动案例界面
                //                intent.setClass(this, ProgressbarActivity.class);
                intent.setClass(this, ZXingActivity.class);
                break;
            case FLAG_JUMP_TO_SERVICE://跳转至服务案例界面
                intent.setClass(this, MyServiceActivity.class);
                break;
            case FLAG_JUMP_TO_BROADCAST_RECEIVER://跳转至广播案例界面

                break;
            case FLAG_JUMP_TO_CONTENT_PROVIDER://跳转至内容提供者案例界面

                break;
            case FLAG_JUMP_TO_ANR://跳转至ANR案例界面
                intent.setClass(this, AnrActivity.class);
                break;

        }

        startActivity(intent);
    }
}
