package oms.mmc.android.fast.framwork.manager.factory;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import java.util.HashMap;
import java.util.Map;

import oms.mmc.android.fast.framwork.manager.MConfig;
import oms.mmc.android.fast.framwork.manager.base.BaseManagerFactory;
import oms.mmc.android.fast.framwork.manager.iml.PerfMonManager;
import oms.mmc.android.fast.framwork.manager.inter.IManager;
import oms.mmc.android.fast.framwork.manager.inter.IManagerFactory;

/**
 * Created by Hezihao on 2017/7/6.
 * 管理类工厂类
 */

public class ManagerFactory extends BaseManagerFactory {
    private static final HashMap<Integer, IManager> managers = new HashMap<Integer, IManager>();

    private static class Singleton {
        private static final ManagerFactory instance = new ManagerFactory();
    }

    public static ManagerFactory getInstance() {
        return Singleton.instance;
    }

    @Override
    public IManagerFactory init(Application app) {
        super.init(app);
        //注册低内存回调，调用性能管理器清除内存
        app.registerComponentCallbacks(new ComponentCallbacks2() {
            @Override
            public void onTrimMemory(int level) {
                ((PerfMonManager) managers.get(MConfig.UUID.PERFMON_MANAGER)).trimMemory(level);
            }

            @Override
            public void onConfigurationChanged(Configuration newConfig) {
            }

            @Override
            public void onLowMemory() {
                ((PerfMonManager) managers.get(MConfig.UUID.PERFMON_MANAGER)).lowMemory();
            }
        });
        return this;
    }

    @Override
    public Context getContext() {
        return getApplication();
    }

    @Override
    public void dispatchInit() {
        //添加性能管理器
        managers.put(MConfig.UUID.PERFMON_MANAGER, PerfMonManager.getInstance());
        for (Map.Entry<Integer, IManager> manager : managers.entrySet()) {
            manager.getValue().init(this);
        }
    }

    @Override
    public void onReady() {
        for (Map.Entry<Integer, IManager> manager : managers.entrySet()) {
            manager.getValue().clearEnv();
        }
    }

    @Override
    public IManager getManager(int uuid) {
        return managers.get(uuid);
    }
}
