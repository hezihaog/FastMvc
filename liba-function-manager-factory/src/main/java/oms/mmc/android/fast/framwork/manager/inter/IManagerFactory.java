package oms.mmc.android.fast.framwork.manager.inter;

import android.app.Application;
import android.content.Context;

/**
 * Created by Hezihao on 2017/7/7.
 */

public interface IManagerFactory {
    IManagerFactory init(Application app);

    void dispatchInit();

    void onReady();

    Application getApplication();

    Context getContext();

    IManager getManager(int flag);
}