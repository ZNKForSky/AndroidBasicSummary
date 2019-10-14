package com.android.basic.summary.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.basic.summary.R;
import com.android.basic.summary.utils.ActivityTaskManager;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    //获取当前类的类名
    public String TAG = this.getClass().getSimpleName();
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: current activity is " + getClass().getSimpleName());
        ActivityTaskManager.getInstance().putActivity(this.getClass().getSimpleName(), this);
        setContentView(getResLayoutId());
        bind = ButterKnife.bind(this);
        initViews();
        initData();
        initListeners();
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

    /**
     * 初始化基础数据
     */
    protected abstract void initData();

    /**
     * 为了避免Handler持有acitivity引用导致内存不会被回收，最终导致内存泄漏的问题
     */
    protected static class MyHandler extends Handler {
        //使该内部类持有外部类MyServiceActivity的弱引用
        private WeakReference<MyServiceActivity> weakReference;

        public MyHandler(MyServiceActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //android.os.Process.myPid() 获取当前程序的进程id
        Log.e(TAG, "onDestroy: android.os.Process.myPid()  == " + android.os.Process.myPid());
        ActivityTaskManager.getInstance().removeActivity(this.getClass().getSimpleName());
        android.os.Process.killProcess(android.os.Process.myPid());//干掉当前进程 PS：此行代码一旦执行，其后面的代码就不会再执行了
        Log.e(TAG, "onDestroy: 我应该不会被执行了~");

        bind.unbind();
    }
}
