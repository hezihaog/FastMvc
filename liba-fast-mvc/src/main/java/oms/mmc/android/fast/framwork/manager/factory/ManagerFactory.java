package oms.mmc.android.fast.framwork.manager.factory;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import oms.mmc.android.fast.framwork.manager.base.BaseManagerFactory;
import oms.mmc.android.fast.framwork.manager.inter.IManager;
import oms.mmc.android.fast.framwork.manager.inter.IManagerFactory;

/**
 * Created by Hezihao on 2017/7/6.
 * 管理类工厂类
 */

public class ManagerFactory extends BaseManagerFactory {
    private static final HashMap<Integer,IManager> managers = new HashMap<Integer, IManager>();

    private static class Singleton {
        private static final ManagerFactory instance = new ManagerFactory();
    }

    public static ManagerFactory getInstance() {
        return Singleton.instance;
    }

    @Override
    public IManagerFactory init(Application app) {
        super.init(app);
        return this;
    }

    @Override
    public Context getContext() {
        return getApplication();
    }

    @Override
    public void dispatchInit() {
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
    public IManager getManager(int flag) {
        return managers.get(flag);
    }
}
