package com.android.basic.summary.utils;

import android.app.Activity;

import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

/**
 * author : Harris Luffy
 * e-mail : 744423651@qq.com
 * date   : 2019/10/10-10:21
 * desc   : Activity管理器
 * version: 1.0
 */
public class ActivityTaskManager {
    //以键值对的形式存放所有activity,方便通过用activity的类名取出对应的Activity
    private HashMap<String, Activity> mActivityMap;
    //本地activity集合
    private static Stack<Activity> mStackActivities;

    private ActivityTaskManager() {
        mActivityMap = new HashMap<>();
        mStackActivities = new Stack<>();
    }

    private static class ActivityTaskManagerBuilder {
        private static final ActivityTaskManager INSTANCE = new ActivityTaskManager();
    }

    /**
     * 返回activity管理器的唯一实例对象(静态内部类实现单例)
     *
     * @return ActivityTaskManager
     */
    public static synchronized ActivityTaskManager getInstance() {
        return ActivityTaskManagerBuilder.INSTANCE;
    }

    /**
     * activity关联到管理器
     * @param name Activity的类名
     * @param activity Activity实例对象
     */
    public void putActivity(String name, Activity activity) {
        mActivityMap.put(name, activity);
        mStackActivities.add(activity);
    }

    /**
     * 得到保存在管理器中的Activity对象
     *
     * @param name Activity的类名
     * @return Activity Activity的实例对象
     */
    public Activity getActivity(String name) {
        return mActivityMap.get(name);
    }

    /**
     * 获取当前的Activity
     *
     * @return Activity
     */
    public static Activity getCurActivity() {
        return mStackActivities.lastElement();
    }

    /**
     * 返回管理器的Activity是否为空
     *
     * @return 当且当管理器中的Activity对象为空时返回true，否则返回false。
     */
    public boolean isEmpty() {
        return mActivityMap.isEmpty();
    }

    /**
     * 返回管理器中Activity对象的个数
     *
     * @return 管理器中Activity对象的个数。
     */
    public int size() {
        return mActivityMap.size();
    }

    /**
     * 返回管理器中是否包含指定的名字
     *
     * @param name 要查找的名字
     * @return 当且仅当包含指定的名字时返回true, 否则返回false
     */
    public boolean containsName(String name) {
        return mActivityMap.containsKey(name);
    }

    /**
     * 返回管理器中是否包含指定的Activity
     *
     * @param activity 要查找的Activity
     * @return 当且仅当包含指定的Activity对象时返回true, 否则返回false。
     */
    public boolean containsActivity(Activity activity) {
        return mActivityMap.containsValue(activity);
    }

    /**
     * 关闭所有活动的Activity
     */
    public void closeAllActivity() {
        Set<String> activityNames = mActivityMap.keySet();
        for (String string : activityNames) {
            finisActivity(mActivityMap.get(string));
        }
        mActivityMap.clear();
    }

    /**
     * 关闭所有活动的Activity除了指定的一个之外
     *
     * @param nameSpecified 指定的不关闭的Activity对象的名字。
     */
    public void closeAllActivityExceptOne(String nameSpecified) {
        Set<String> activityNames = mActivityMap.keySet();
        Activity activitySpecified = mActivityMap.get(nameSpecified);
        for (String name : activityNames) {
            if (!name.equals(nameSpecified)) {
                finisActivity(mActivityMap.get(name));
            }
        }
        mActivityMap.clear();
        mActivityMap.put(nameSpecified, activitySpecified);
    }

    /**
     * 移除Activity对象,如果它未结束则结束它
     *
     * @param name Activity对象的名字
     */
    public void removeActivity(String name) {
        Activity activity = mActivityMap.remove(name);
        if (activity != null) {
            mStackActivities.remove(activity);
        }
        finisActivity(activity);
    }

    /**
     * 关闭指定名称的activity，如果存在
     *
     * @param name
     */
    public void closeActivity(String name) {
        if (containsName(name)) {
            removeActivity(name);
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param activity 指定的Activity
     */
    private final void finisActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }

}
