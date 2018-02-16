package oms.mmc.android.fast.framwork;

import android.content.res.Configuration;
import android.os.Handler;

import oms.mmc.android.fast.framwork.base.ManagerContext;


/**
 * Package: oms.mmc.android.fast.framwork
 * FileName: BaseMMCFastApplication
 * Date: on 2018/1/18  下午5:28
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class BaseMMCFastApplication extends ManagerContext {
    private static BaseMMCFastApplication instance;
    private Handler mMainHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mMainHandler = new Handler(getMainLooper());
    }

    public void postDelayed(Runnable runnable, long duration) {
        mMainHandler.postDelayed(runnable, duration);
    }

    public void post(Runnable runnable) {
        mMainHandler.post(runnable);
    }

    public static BaseMMCFastApplication getInstance() {
        return instance;
    }

    public Handler getMainHandler() {
        return mMainHandler;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}