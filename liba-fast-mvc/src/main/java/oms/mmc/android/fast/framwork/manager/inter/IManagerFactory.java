package oms.mmc.android.fast.framwork.manager.inter;

import android.app.Application;
import android.content.Context;

/**
 * Created by Hezihao on 2017/7/7.
 */

public interface IManagerFactory {
    /**
     * 初始化
     *
     * @param app application
     */
    IManagerFactory init(Application app);

    /**
     * 初始化manager
     */
    void dispatchInit();

    /**
     * 初始化完成
     */
    void onReady();

    /**
     * 获取application
     */
    Application getApplication();

    /**
     * 获取Context
     */
    Context getContext();

    /**
     * 根据uuid获取到manger实例
     *
     * @param uuid manager的uuid
     */
    IManager getManager(int uuid);
}