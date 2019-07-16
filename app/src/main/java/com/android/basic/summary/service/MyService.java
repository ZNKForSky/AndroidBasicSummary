package com.android.basic.summary.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.basic.summary.R;
import com.android.basic.summary.activity.MyServiceActivity;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private static final int START_FOREGROUND_ID = 520;
    private static final String NOTIFICATION_ID = "channelId";
    private static final String NOTIFICATION_NAME = "channelName";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        startForeground(START_FOREGROUND_ID, getNotifaction());
    }

    /**
     * 每次通过startService()方法启动Service时都会被回调
     *
     * @param intent  startService(Intent intent)中的intent
     * @param flags   启动服务的方式
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);

    }

    /**
     * 绑定服务时才会被调用
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: ");
        return new DownloadBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        stopForeground(true);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: ");
//        return super.onUnbind(intent);
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.e(TAG, "onRebind: ");
    }

    public class DownloadBinder extends Binder {
        public void startDownload() {
            Log.e(TAG, "startDownload: ");
        }

        public int getDownloadProgress() {
            Log.e(TAG, "getDownloadProgress: ");
            return 0;
        }
    }

    private Notification getNotifaction() {
        NotificationManager notificationManager;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MyServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.move_my_heart_by_xuan))
                .setContentText(getString(R.string.the_oath_of_love))
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.heart)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.heart))
                .setContentIntent(pendingIntent);
        //设置Notification的ChannelID,否则不能正常显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(NOTIFICATION_ID);
        }
        Notification notification = builder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_ID, NOTIFICATION_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        return notification;
    }

}
