package com.android.basic.summary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.basic.summary.service.MyService;
import com.android.basic.summary.MyServiceConnection;
import com.android.basic.summary.R;

public class AnrActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnExcAnr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);
        initView();
    }

    private void initView() {
        mBtnExcAnr = findViewById(R.id.btn_exc_anr);

        mBtnExcAnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这是Android提供线程休眠函数，与Thread.sleep() 最大的区别是
                //该使用该函数不会抛出InterruptedException异常。
                SystemClock.sleep(20 * 1000);
            }
        });

//        WindowManager wm = (WindowManager)getSystemService(getApplication().WINDOW_SERVICE);
//        //布局参数layoutParams相关设置略
//        View view=LayoutInflater.from(getApplication()).inflate(R.layout.activity_anr, null);
//        //添加view
//        wm.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MyService.class);
        MyServiceConnection myServiceConnection = new MyServiceConnection();
        switch (v.getId()) {
            case R.id.btn_start_service:
                AnrActivity.this.startService(intent);
                break;
            case R.id.btn_stop_service:
                AnrActivity.this.stopService(intent);
                break;
            case R.id.btn_bind_service:
                AnrActivity.this.bindService(intent,myServiceConnection,Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                AnrActivity.this.unbindService(myServiceConnection );
                break;
        }
    }
}
