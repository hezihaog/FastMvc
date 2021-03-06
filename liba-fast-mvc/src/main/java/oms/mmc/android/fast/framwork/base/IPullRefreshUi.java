package oms.mmc.android.fast.framwork.base;

import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IPullRefreshAction
 * Date: on 2018/3/21  下午9:54
 * Auther: zihe
 * Descirbe:下拉刷新界面需要实现的接口
 * Email: hezihao@linghit.com
 */

public interface IPullRefreshUi<P extends IPullRefreshLayout> {
    /**
     * 回调界面使用对应的下拉刷新控件来生成对应的包裹类
     *
     * @param pullRefreshAbleView 布局中使用的下拉刷新控件
     */
    IPullRefreshWrapper<P> onInitPullRefreshWrapper(P pullRefreshAbleView);

    /**
     * 下拉刷新包裹类初始化完毕后回调，统一在这里下拉刷新控件进行一些相关设置
     *
     * @param refreshWrapper      下拉刷新包裹类
     * @param pullRefreshAbleView 下拉刷新控件
     */
    void onPullRefreshWrapperReady(IPullRefreshWrapper<P> refreshWrapper, P pullRefreshAbleView);
}