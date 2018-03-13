package oms.mmc.android.fast.framwork.base;

import android.support.v4.app.FragmentManager;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IFragmentOperator
 * Date: on 2018/3/13  上午11:47
 * Auther: zihe
 * Descirbe:操作器接口
 * Email: hezihao@linghit.com
 */

public interface IFragmentOperator extends IFragmentAction {
    /**
     * 获取操作器
     */
    IFragmentOperator getFragmentOperator();

    /**
     * 设置Fragment管理器
     */
    void setSupportFragmentManager(FragmentManager supportFragmentManager);

    /**
     * 获取Fragment管理器
     */
    FragmentManager getSupportFragmentManager();
}