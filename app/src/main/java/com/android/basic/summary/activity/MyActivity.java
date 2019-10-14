package com.android.basic.summary.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.basic.summary.R;
import com.android.basic.summary.activity.ui.AlertDialogActivity;
import com.android.basic.summary.activity.ui.ProgressbarActivity;
import com.android.basic.summary.adapter.MyActivityAdapter;
import com.android.basic.summary.utils.ActivityTaskManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyActivity extends BaseActivity {

    @BindView(R.id.rv_my_activity)
    RecyclerView rvMyActivity;
    @BindView(R.id.edt_test)
    EditText mEdtTest;
    @BindView(R.id.btn_quit_app)
    Button mBtnQuitApp;
    private ArrayList<String> mActivityNames;//此项目中所有activity的名字的集合
    private MyActivityAdapter myActivityAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            String key_info = (String) savedInstanceState.get("key_info");
            mEdtTest.setText(key_info);
        }
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.activity_my;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initData() {
        if (mActivityNames == null) {
            mActivityNames = new ArrayList<>();
        }
        mActivityNames.add("AlertDialogActivity");
        mActivityNames.add("ProgressbarActivity");
        mActivityNames.add("AnrActivity");
        mActivityNames.add("DeviceInfoActivity");
        mActivityNames.add("FadeNotifactionServiceActivity");
        mActivityNames.add("LaunchModeActivity");
        mActivityNames.add("PopupWindowActivity");
        mActivityNames.add("ZXingActivity");
        mActivityNames.add("LifeCycleActivity");
        myActivityAdapter = new MyActivityAdapter(mActivityNames, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMyActivity.setLayoutManager(linearLayoutManager);
        rvMyActivity.setAdapter(myActivityAdapter);
    }


    @Override
    protected void initListeners() {
        myActivityAdapter.setOnItemClickListener(new MyActivityAdapter.OnItemClickListener() {

            private Intent intent;

            @Override
            public void onItemClick(int position) {
                if (intent == null) {
                    intent = new Intent();
                }
                switch (position) {
                    case 0:
                        intent.setClass(MyActivity.this, AlertDialogActivity.class);
                        break;
                    case 1:
                        intent.setClass(MyActivity.this, ProgressbarActivity.class);
                        break;
                    case 2:
                        intent.setClass(MyActivity.this, AnrActivity.class);
                        break;
                    case 3:
                        intent.setClass(MyActivity.this, DeviceInfoActivity.class);
                        break;
                    case 4:
                        intent.setClass(MyActivity.this, FadeNotifactionServiceActivity.class);
                        break;
                    case 5:
                        intent.setClass(MyActivity.this, LaunchModeActivity.class);
                        break;
                    case 6:
                        intent.setClass(MyActivity.this, PopupWindowActivity.class);
                        break;
                    case 7:
                        intent.setClass(MyActivity.this, ZXingActivity.class);
                        break;
                    case 8:
                        intent.setClass(MyActivity.this, LifeCycleActivity.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
            }
        });
        mBtnQuitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityTaskManager.getInstance().closeAllActivity();
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String s = mEdtTest.getText().toString();
        if (!TextUtils.isEmpty(s)) {
            outState.putString("key_info", s);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

}
