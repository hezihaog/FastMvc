package oms.mmc.android.fast.framwork.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Package: oms.mmc.android.fast.framwork.config
 * FileName: AppConfigManager
 * Date: on 2018/3/15  上午11:17
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class AppConfigManager {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private AppConfigManager() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull Context context) {
        AppConfigManager.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}