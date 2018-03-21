package oms.mmc.android.fast.framwork.sample;

import android.content.Context;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.squareup.leakcanary.LeakCanary;

import oms.mmc.android.fast.framwork.BaseFastApplication;
import oms.mmc.android.fast.framwork.config.AppConfigManager;
import oms.mmc.android.fast.framwork.manager.factory.ManagerFactory;
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
        ManagerFactory.getInstance().init(this);
        CacheManager.getInstance().init(this);
        AppConfigManager.init(this);
        //设置演示的下拉刷新库的刷新头
        TwinklingRefreshLayout.setDefaultHeader(SinaRefreshView.class.getName());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }
}