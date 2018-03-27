package oms.mmc.android.fast.framwork.util;

import android.app.Activity;

import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.factory.load.factory.ILoadViewFactory;

/**
 * Rv帮助类的接口
 */
public interface IViewHelper {
    /**
     * 刷新
     */
    void refresh();

    /**
     * 加载更多
     */
    void loadMore();

    /**
     * 获取Activity
     */
    Activity getActivity();

    /**
     * 获取界面切换的加载布局
     */
    ILoadViewFactory.ILoadView getLoadView();

    /**
     * 加载更多条目布局
     */
    ILoadMoreViewFactory.ILoadMoreView getLoadMoreView();
}