package com.android.basic.summary;

import android.app.Application;

import com.hjq.toast.ToastUtils;

/**
 * @author Harris Luffy
 * @version 1.0
 * @des
 * e-mail : 744423651@qq.com
 * @updateDes 2019/9/11 9:13
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 在 Application 中初始化
        ToastUtils.init(this);
    }
}
