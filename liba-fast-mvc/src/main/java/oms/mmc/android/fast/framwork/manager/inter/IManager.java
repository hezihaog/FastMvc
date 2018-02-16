package oms.mmc.android.fast.framwork.manager.inter;

import android.app.Application;
import android.content.Context;

/**
 * Created by Hezihao on 2017/7/6.
 * 管理类接口
 */

public interface IManager {
    /**
     * 初始化
     * @param factory 工厂
     */
    IManager init(IManagerFactory factory);

    /**
     * 获取工厂实例
     */
    IManagerFactory getFactory();

    Application getApplication();

    Context getContext();

    /**
     * 清理环境
     */
    void clearEnv();
}