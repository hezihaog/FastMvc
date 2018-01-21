package oms.mmc.android.fast.framwork.util;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: MMCFastActivityManager
 * Date: on 2018/1/18  下午5:31
 * Auther: zihe
 * Descirbe:Activity堆栈式管理
 * Email: hezihao@linghit.com
 */

public class MMCFastActivityManager {
    private static Stack<Activity> activityStack;

    private MMCFastActivityManager() {
    }

    private static class Singleton {
        private static final MMCFastActivityManager instance = new MMCFastActivityManager();
    }

    /**
     * 单一实例
     */
    public static MMCFastActivityManager getActivityManager() {
        return Singleton.instance;
    }

    /**
     * 获取指定的Activity
     */
    public static Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        try {
            Activity activity = activityStack.lastElement();
            return activity;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        try {
            Activity activity = activityStack.lastElement();
            finishActivity(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
                break;
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }

    /**
     * 获取堆栈
     */
    public Stack<Activity> getActivityStack() {
        return activityStack;
    }
}