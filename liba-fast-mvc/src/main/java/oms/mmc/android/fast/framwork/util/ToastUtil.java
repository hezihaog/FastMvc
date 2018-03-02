package oms.mmc.android.fast.framwork.util;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;


/**
 * Created by Hezihao on 2017/7/6.
 * Toast 工具类
 */

public class ToastUtil {
    public static final String TAG = ToastUtil.class.getSimpleName();
    private static String lastToast = "";
    private static long lastToastTime;
    private static Toast toast;

    private ToastUtil() {
        throw new RuntimeException("tool class I can't be instance");
    }

    /**
     * 获取资源id对应的字符串
     */
    public static String getString(Context context, int id) {
        return context.getApplicationContext().getResources().getString(id);
    }

    /**
     * 以资源id显示短Toast信息
     */
    public static void showToast(Context context, int message) {
        toast(context, getString(context, message), Toast.LENGTH_SHORT);
    }

    /**
     * 以直接字符串显示短Toast信息
     */
    public static void showToast(Context context, String message) {
        toast(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 以资源id显示长Toast信息
     */
    public static void showLongToast(Context context, int message) {
        toast(context, getString(context, message), Toast.LENGTH_SHORT);
    }

    /**
     * 以直接字符串显示长Toast信息
     */
    public static void showLongToast(Context context, String message) {
        toast(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast信息
     */
    private static void toast(final Context context, final String message, final int showTime) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Handler uiHandler = new Handler(context.getApplicationContext().getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(message)) {
                    Log.d(TAG, message);
                    long time = System.currentTimeMillis();
                    if (!message.equalsIgnoreCase(lastToast) || Math.abs(time - lastToastTime) > 2000) {
                        if (toast == null) {
                            toast = Toast.makeText(context.getApplicationContext(), "", showTime);
                            toast.setGravity(Gravity.BOTTOM, 0, 100);
                            toast.setDuration(Toast.LENGTH_SHORT);
                        }
                        toast.setText(message);
                        toast.show();
                        lastToast = message;
                        lastToastTime = System.currentTimeMillis();
                    }
                }
            }
        });
    }
}
