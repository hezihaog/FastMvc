package oms.mmc.android.fast.framwork;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Handler;

import oms.mmc.android.fast.framwork.base.IHandlerDispatcher;


/**
 * Package: oms.mmc.android.fast.framwork
 * FileName: BaseMMCFastApplication
 * Date: on 2018/1/18  下午5:28
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class BaseFastApplication extends Application implements IHandlerDispatcher {
    @SuppressLint("StaticFieldLeak")
    private static BaseFastApplication mInstance;
    private Handler mUIHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mUIHandler = initUiHandler();
    }

    public static BaseFastApplication getInstance() {
        return mInstance;
    }

    @Override
    public Handler initUiHandler() {
        return new Handler(getMainLooper());
    }

    @Override
    public Handler getUiHandler() {
        if (mUIHandler == null) {
            mUIHandler = initUiHandler();
        }
        return mUIHandler;
    }

    @Override
    public void post(Runnable runnable) {
        mUIHandler.post(runnable);
    }

    @Override
    public void postDelayed(Runnable runnable, long duration) {
        mUIHandler.postDelayed(runnable, duration);
    }

    @Override
    public void removeUiHandlerMessage(Runnable runnable) {
        mUIHandler.removeCallbacks(runnable);
    }

    @Override
    public void removeUiHandlerAllMessage() {
        mUIHandler.removeCallbacksAndMessages(null);
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