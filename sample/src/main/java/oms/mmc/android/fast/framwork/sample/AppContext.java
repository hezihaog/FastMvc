package oms.mmc.android.fast.framwork.sample;

import android.content.Context;

import oms.mmc.android.fast.framwork.BaseFastApplication;

/**
 * Package: PACKAGE_NAME
 * FileName: AppContext
 * Date: on 2018/1/24  下午2:22
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class AppContext extends BaseFastApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }
}