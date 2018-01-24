package oms.mmc.android.fast.framwork.basiclib.base;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.util.ArrayMap;

import oms.mmc.android.fast.framwork.basiclib.util.PropertyUtil;
import oms.mmc.android.fast.framwork.basiclib.util.TDevice;
import oms.mmc.android.fast.framwork.basiclib.util.ToastUtil;
import oms.mmc.android.fast.framwork.manager.factory.ManagerFactory;

/**
 * Created by Hezihao on 2017/7/13.
 * 提供管理器、Toast、download、Preferences
 */
@SuppressLint("Registered")
public abstract class ManagerContext extends BaseApplication {
    private static ManagerContext instance;

    public static ManagerContext getInstance() {
        return instance;
    }

    public static void showToast(int message) {
        ToastUtil.showToast(message);
    }

    public static void showToast(final String message) {
        ToastUtil.showToast(message);
    }

    public static void showToastWithIcon(final String message) {
        ToastUtil.showToastWithIcon(message);
    }

    public static void showToastWithIcon(final String message, final int iconResId) {
        ToastUtil.showToastWithIcon(message, iconResId);
    }

    public static void setProperty(String key, int value) {
        PropertyUtil.setProperty(key, value);
    }

    public static void setProperty(String key, boolean value) {
        PropertyUtil.setProperty(key, value);
    }

    public static void setProperty(String key, String value) {
        PropertyUtil.setProperty(key, value);
    }

    public static void setProperty(String key, long value) {
        PropertyUtil.setProperty(key, value);
    }

    public static void setProperty(String key, float value) {
        PropertyUtil.setProperty(key, value);
    }

    public static void setProperty(ArrayMap<String, Object> map) {
        PropertyUtil.setProperty(map);
    }

    public static void removeProperty(String key) {
        PropertyUtil.removeProperty(key);
    }

    public static boolean getProperty(String key, boolean defValue) {
        return PropertyUtil.getProperty(key, defValue);
    }

    public static String getProperty(String key, String defValue) {
        return PropertyUtil.getProperty(key, defValue);
    }

    public static int getProperty(String key, int defValue) {
        return PropertyUtil.getProperty(key, defValue);
    }

    public static long getProperty(String key, long defValue) {
        return PropertyUtil.getProperty(key, defValue);
    }

    public static float getProperty(String key, float defValue) {
        return PropertyUtil.getProperty(key, defValue);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ManagerFactory.getInstance().init(this);
    }

    /**
     * 强制默认字体
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (TDevice.isMainProcess(this)) {
            if (newConfig.fontScale != 1) {
                getResources();
            }
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    public ManagerFactory getManagerFactory() {
        return ManagerFactory.getInstance();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
//        if (TDevice.isMainProcess(this)) {
//            PerfMonManager.trimMemory(level);
//        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        if (TDevice.isMainProcess(this)) {
//            PerfMonManager.lowMemory();
//        }
    }
}
