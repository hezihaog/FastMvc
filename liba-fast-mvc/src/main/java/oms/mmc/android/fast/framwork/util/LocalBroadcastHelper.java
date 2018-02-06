package oms.mmc.android.fast.framwork.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Package: oms.mmc.android.fast.framwork.sample.util
 * FileName: BroadcastHelper
 * Date: on 2018/2/6  上午11:27
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class LocalBroadcastHelper {
    public static final String ACTION_LOAD_MORE = "load_more_action";

    /**
     * 获取本地广播管理器
     */
    private static LocalBroadcastManager getLocalBroadcastManager(Context context) {
        return LocalBroadcastManager.getInstance(context);
    }

    /**
     * 注册
     */
    public static void register(Context context, String action, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter(action);
        getLocalBroadcastManager(context).registerReceiver(receiver, filter);
    }

    /**
     * 注销
     */
    public static void unRegister(Context context, BroadcastReceiver receiver) {
        getLocalBroadcastManager(context).unregisterReceiver(receiver);
    }

    public static void registerLoadMore(Context context, BroadcastReceiver receiver) {
        register(context, LocalBroadcastHelper.ACTION_LOAD_MORE, receiver);
    }

    /**
     * 发送加载更多
     */
    public static void sendLoadMore(Context context, Intent intent) {
        sendBroadcast(context, intent, LocalBroadcastHelper.ACTION_LOAD_MORE);
    }

    /**
     * 发送广播
     */
    private static void sendBroadcast(Context context, String action) {
        sendBroadcast(context, action);
    }

    /**
     * 发送广播支持intent
     */
    private static void sendBroadcast(Context context, Intent intent, String action) {
        if (intent == null) {
            intent = new Intent(action);
        }
        intent.setAction(action);
        getLocalBroadcastManager(context).sendBroadcast(intent);
    }
}