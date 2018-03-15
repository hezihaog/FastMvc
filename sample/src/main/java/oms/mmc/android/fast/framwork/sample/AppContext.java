package oms.mmc.android.fast.framwork.sample;

import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

import oms.mmc.android.fast.framwork.BaseFastApplication;
import oms.mmc.cache.CacheManager;

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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        //管理器工厂
        //ManagerFactory.getInstance().init(this);
        CacheManager.getInstance().init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }
}