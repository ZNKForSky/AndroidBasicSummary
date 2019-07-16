package com.android.basic.summary;


import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.android.basic.summary.service.MyService;

public class MyServiceConnection implements ServiceConnection {
    private static final String TAG = "MyServiceConnection";
    public MyService.DownloadBinder downloadBinder;

    /**
     * 绑定成功后该方法被执行。在该方法中，可以通过把IBinder对象向下转型，取得Service中onBind方法的返回值
     *
     * @param name
     * @param service
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        downloadBinder = (MyService.DownloadBinder) service;
        downloadBinder.startDownload();
        downloadBinder.getDownloadProgress();
        Log.e(TAG, "Service Connection Success");
    }

    /**
     * 解绑后该方法被执行执行
     *
     * @param name
     */
    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e(TAG, "Service Connection Filed");
    }
}
