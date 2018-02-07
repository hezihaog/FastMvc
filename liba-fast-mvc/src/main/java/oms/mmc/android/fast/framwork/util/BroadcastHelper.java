package oms.mmc.android.fast.framwork.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Package: oms.mmc.android.fast.framwork.sample.util
 * FileName: BroadcastHelper
 * Date: on 2018/2/6  上午11:27
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class BroadcastHelper {
    /**
     * 注册
     */
    public static void register(Context context, String action, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter(action);
        context.registerReceiver(receiver, filter);
    }

    /**
     * 注销
     */
    public static void unRegister(Context context, BroadcastReceiver receiver) {
        context.unregisterReceiver(receiver);
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
    public static void sendBroadcast(Context context, Intent intent, String action) {
        if (intent == null) {
            intent = new Intent(action);
        }
        intent.setAction(action);
        context.sendBroadcast(intent);
    }
}