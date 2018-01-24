package oms.mmc.android.fast.framwork.manager.base;

import android.app.Application;
import android.content.Context;

import oms.mmc.android.fast.framwork.manager.inter.IManager;
import oms.mmc.android.fast.framwork.manager.inter.IManagerFactory;

/**
 * Created by Hezihao on 2017/7/7.
 */

public abstract class BaseManager implements IManager {
    private IManagerFactory mFactory;

    @Override
    public IManager init(IManagerFactory factory) {
        mFactory = factory;
        return this;
    }

    @Override
    public IManagerFactory getFactory() {
        return mFactory;
    }

    @Override
    public Application getApplication() {
        return mFactory.getApplication();
    }

    @Override
    public Context getContext() {
        return mFactory.getContext();
    }

    @Override
    public void clearEnv() {

    }
}
