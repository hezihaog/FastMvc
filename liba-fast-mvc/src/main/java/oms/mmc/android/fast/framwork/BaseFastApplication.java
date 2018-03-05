package oms.mmc.android.fast.framwork;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Handler;

import oms.mmc.android.fast.framwork.base.BaseApplication;
import oms.mmc.android.fast.framwork.base.IHandlerDispatcher;


/**
 * Package: oms.mmc.android.fast.framwork
 * FileName: BaseMMCFastApplication
 * Date: on 2018/1/18  下午5:28
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class BaseFastApplication extends BaseApplication implements IHandlerDispatcher {
    @SuppressLint("StaticFieldLeak")
    private static BaseFastApplication mInstance;
    private Handler mMainHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mMainHandler = initHandler();
    }

    public static BaseFastApplication getInstance() {
        return mInstance;
    }

    @Override
    public Handler initHandler() {
        return new Handler(getMainLooper());
    }

    public void post(Runnable runnable) {
        mMainHandler.post(runnable);
    }

    public void postDelayed(Runnable runnable, long duration) {
        mMainHandler.postDelayed(runnable, duration);
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