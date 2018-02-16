package oms.mmc.android.fast.framwork.manager.base;

import android.app.Application;

import oms.mmc.android.fast.framwork.manager.inter.IManagerFactory;

/**
 * Created by Hezihao on 2017/7/7.
 */

public abstract class BaseManagerFactory implements IManagerFactory {
    private Application app;

    @Override
    public IManagerFactory init(Application app) {
        this.app = app;
        getContext();
        dispatchInit();
        onReady();
        return this;
    }

    @Override
    public Application getApplication() {
        return app;
    }
}