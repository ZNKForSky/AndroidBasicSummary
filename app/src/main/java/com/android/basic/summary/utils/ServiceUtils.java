package com.android.basic.summary.utils;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

public class ServiceUtils {
    private static final String TAG = "ServiceUtils";

    /**
     * 校验某个服务是否还存在
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        // 校验服务是否还存在
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        //关于AM的getRunningServices，Android官方解释是这样的：此方法不再适用于第三方应用程序。为了向后兼容，
        //它仍将返回调用者自己的服务。
        List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : services) {
            // 得到所有正在运行的服务的名称
            String name = info.service.getClassName();
            Log.e(TAG, "isServiceRunning: name == " + name);
            if (serviceName.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
