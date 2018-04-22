package oms.mmc.android.fast.framwork.util;

import android.app.Activity;

import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.base.IScrollableAdapterView;

/**
 * Rv帮助类的接口
 */
public interface IViewHelper<Model> {
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

    /**
     * 是否在加载中
     */
    boolean isLoading();

    /**
     * 获取滚动控件
     */
    IScrollableAdapterView getScrollableView();

    /**
     * 获取上次刷新数据的时间（数据成功的加载），如果数据没有加载成功过，那么返回-1
     */
    long getLastLoadTime();

    /**
     * 是否成功加载过
     */
    boolean isLoaded();

    /**
     * 设置列表控件适配器
     *
     * @param adapter 适配器
     */
    void setListAdapter(ICommonListAdapter<Model> adapter);

    /**
     * 获取列表适配器
     */
    ICommonListAdapter<Model> getListAdapter();

    /**
     * 设置数据源
     *
     * @param dataSource 数据源
     */
    void setDataSource(IDataSource<Model> dataSource);

    /**
     * 获取设置的数据源
     */
    IDataSource<Model> getDataSource();

    /**
     * 获取自身
     */
    ListHelper getListHelper();

    /**
     * 获取刷新布局包裹类
     */
    IPullRefreshWrapper<?> getPullRefreshWrapper();

    /**
     * 获取刷新布局
     */
    IPullRefreshLayout getPullRefreshLayout();

    /**
     * 开始刷新并且显示加载动画
     */
    void startRefreshWithRefreshLoading();

    /**
     * 当前是否正在刷新
     */
    boolean isRefreshing();

    /**
     * 是否有下一页
     */
    boolean isHasMoreData();

    /**
     * 设置是否可以下拉刷新
     *
     * @param canPullToRefresh 是否可以下拉刷新
     */
    void setCanPullToRefresh(boolean canPullToRefresh);

    /**
     * 当前是否可以下拉刷新
     */
    boolean isCanPullToRefresh();

    /**
     * 设置列表布局反转
     *
     * @param reverse 是否反转列表布局
     */
    void setReverse(boolean reverse);

    /**
     * 当前是否反转列表布局
     */
    boolean isReverse();

    /**
     * 设置加载更多尾部是否展示
     *
     * @param enableLoadMoreFooter 加载更多尾部条目是否展示
     */
    void setEnableLoadMoreFooter(boolean enableLoadMoreFooter);

    /**
     * 是否展示加载更多布局
     */
    boolean isEnableLoadMoreFooter();

    /**
     * 获取列表滚动帮助类
     */
    ListScrollHelper getListScrollHelper();
}