package oms.mmc.android.fast.framwork.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.util.IViewFinder;

/**
 * 普通界面布局回调接口
 */
public interface LayoutCallback {
    /**
     * 提供布局资源前回调
     */
    void onLayoutBefore();

    /**
     * 提供布局回调，子类复写返回当前页面使用的布局View
     *
     * @return 布局layoutView
     */
    View onLayoutView(LayoutInflater inflater, ViewGroup container);

    /**
     * 开始查找控件
     *
     * @param finder 布局View持有器
     */
    void onFindView(IViewFinder finder);

    /**
     * 页面View初始化完成回调，子类可复写对view进一步处理
     */
    void onLayoutAfter();
}