package com.android.basic.summary;

import android.app.Application;

import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastQQStyle;

/**
 * @author Harris Luffy
 * @version 1.0
 * @des e-mail : 744423651@qq.com
 * @updateDes 2019/9/11 9:13
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化ToastUtils,并设置为QQ的风格
        ToastUtils.init(this, new ToastQQStyle(this));
    }
}
