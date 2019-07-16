package com.android.basic.summary.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.basic.summary.service.MyIntentService;
import com.android.basic.summary.service.MyService;
import com.android.basic.summary.MyServiceConnection;
import com.android.basic.summary.R;
import com.android.basic.summary.utils.ServiceUtils;

import java.lang.ref.WeakReference;


public class MyServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MyServiceActivity";
    private TextView mTvWelcome;
    private Button mBtnStartService;
    private Button mBtnStopService;
    private Button mBtnBindService;
    private Button mBtnUnbindService;
    private Button mBtnFadeNotifactionService;
    private Button mBtnStartIntentService;
    public static final int UPDATE_TETX = 1;
    private MyServiceConnection myServiceConnection;
    private Intent intent;
    private boolean isBinded;//用于判断服务是否解绑，默认解绑
    private TimeTickReceiver timeTickReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: current page is " + TAG);
        setContentView(R.layout.activity_my_service);
        initViews();
        initListeners();
        //注册系统广播Intent.ACTION_TIME_TICK，这个广播每分钟发送一次
        registerBroadcast();

    }

    private void initViews() {
        mTvWelcome = findViewById(R.id.tv_welcome);
        mBtnStartService = findViewById(R.id.btn_start_service);
        mBtnStopService = findViewById(R.id.btn_stop_service);
        mBtnBindService = findViewById(R.id.btn_bind_service);
        mBtnUnbindService = findViewById(R.id.btn_unbind_service);
        mBtnFadeNotifactionService = findViewById(R.id.btn_jump_to_fade_notifaction_service);
        mBtnStartIntentService = findViewById(R.id.btn_start_intent_service);

    }

    private void initListeners() {
        mTvWelcome.setOnClickListener(this);
        mBtnStartService.setOnClickListener(this);
        mBtnStopService.setOnClickListener(this);
        mBtnBindService.setOnClickListener(this);
        mBtnUnbindService.setOnClickListener(this);
        mBtnFadeNotifactionService.setOnClickListener(this);
        mBtnStartIntentService.setOnClickListener(this);
    }

    //创建Handler对象
    private MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        //使该内部类持有外部类MyServiceActivity的弱引用
        private WeakReference<MyServiceActivity> weakReference;

        public MyHandler(MyServiceActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TETX:
                    MyServiceActivity myServiceActivity = weakReference.get();
                    myServiceActivity.mTvWelcome.setText(myServiceActivity.getString(R.string.string_welcome));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (myServiceConnection == null) {
            myServiceConnection = new MyServiceConnection();
        }
        if (intent == null) {
            intent = new Intent(this, MyService.class);
        }
        switch (v.getId()) {
            case R.id.btn_start_service://开启服务
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                } else {
                    startService(intent);
                }
//                startService(intent);
                break;
            case R.id.btn_stop_service://关闭服务
                stopService(intent);
                break;
            case R.id.btn_bind_service://绑定服务
                if (bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE)) {
                    isBinded = true;
                }
                break;
            case R.id.btn_unbind_service://解绑服务
                if (isBinded) {
                    unbindService(myServiceConnection);
                    isBinded = false;
                }
                break;
            case R.id.tv_welcome://在子线程中改变文字，会报android.view.ViewRootImpl$CalledFromWrongThreadException这个异常
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //耗时操作执行完后，发送消息给handler处理
                        Message message = Message.obtain();
                        message.what = UPDATE_TETX;
                        myHandler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.btn_jump_to_fade_notifaction_service://跳转到隐藏通知栏的前台服务界面
                Intent intent = new Intent(this, FadeNotifactionServiceActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_start_intent_service://开启IntentService
                Log.e(TAG, "onClick: MainThread id is " + Thread.currentThread().getId());
                Intent intentService = new Intent(this, MyIntentService.class);
                startService(intentService);
                break;
        }
    }

    class TimeTickReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: Intent.ACTION_TIME_TICK");
            if (!ServiceUtils.isServiceRunning(MyServiceActivity.this, "com.android.basic.summary.service.MyService")) {
                Intent intentStartService = new Intent(MyServiceActivity.this, MyService.class);
                startService(intentStartService);
            }
        }
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        timeTickReceiver = new TimeTickReceiver();
        //注册广播接收
        registerReceiver(timeTickReceiver, filter);
    }

    //子线程是可以更新UI的
    class TestThread extends Thread {
        @Override
        public void run() {
            Looper.prepare();
            TextView tx = new TextView(MyServiceActivity.this);
            tx.setText("时间煮雨");
            tx.setTextColor(getResources().getColor(R.color.white));
            tx.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            tx.setGravity(Gravity.CENTER);
            WindowManager wm = MyServiceActivity.this.getWindowManager();
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    250, 150, 200, 200, WindowManager.LayoutParams.FIRST_SUB_WINDOW,
                    WindowManager.LayoutParams.TYPE_TOAST, PixelFormat.OPAQUE);
            wm.addView(tx, params);
            Looper.loop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeTickReceiver != null) {
            unregisterReceiver(timeTickReceiver);
        }
    }
}
