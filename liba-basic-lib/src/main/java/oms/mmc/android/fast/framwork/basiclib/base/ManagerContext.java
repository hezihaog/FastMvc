package oms.mmc.android.fast.framwork.basiclib.base;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.Resources;

import oms.mmc.android.fast.framwork.basiclib.util.TDevice;
import oms.mmc.android.fast.framwork.manager.factory.ManagerFactory;

/**
 * Created by Hezihao on 2017/7/13.
 */
@SuppressLint("Registered")
public abstract class ManagerContext extends BaseApplication {
    private static ManagerContext instance;

    public static ManagerContext getInstance() {
        return instance;
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
