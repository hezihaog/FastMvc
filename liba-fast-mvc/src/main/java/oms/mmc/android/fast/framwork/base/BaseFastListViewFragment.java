package oms.mmc.android.fast.framwork.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.util.ListAbleDelegateHelper;
import oms.mmc.android.fast.framwork.util.ListViewListAbleDelegateHelper;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.list.lv.CommonListViewAdapter;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ListViewListConfigCallback;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.base.IScrollableListAdapterView;
import oms.mmc.helper.base.IScrollableViewWrapper;
import oms.mmc.helper.widget.ScrollableListView;
import oms.mmc.helper.wrapper.ScrollableListViewWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: BaseFastListViewFragment
 * Date: on 2018/3/28  下午11:59
 * Auther: zihe
 * Descirbe:ListView控件使用的Fragment
 * Email: hezihao@linghit.com
 */

public abstract class BaseFastListViewFragment<P extends IPullRefreshLayout, V extends ScrollableListView> extends BaseFastListFragment<P, V>
        implements ListViewListConfigCallback {
    /**
     * 使用ListView的通用布局视图，如界面没有特殊需求，可以不重写该方法，直接使用父类定义的即可
     */
    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_base_fast_list_view_list, container, false);
    }

    @Override
    public ListAbleDelegateHelper<P, V> onInitListAbleDelegateHelper() {
        return new ListViewListAbleDelegateHelper<P, V>(this, this, this);
    }

    /**
     * 初始化下拉刷新控件包裹类，需要类旁定义的一致
     *
     * @param pullRefreshAbleView 布局中使用的下拉刷新控件
     */
    @Override
    public IPullRefreshWrapper<P> onInitPullRefreshWrapper(P pullRefreshAbleView) {
        return (IPullRefreshWrapper<P>) new SwipePullRefreshWrapper((SwipePullRefreshLayout) pullRefreshAbleView);
    }

    /**
     * 初始化下拉控件完毕的回调，在这里调用设置的刷新布局中特殊的方法，例如设置禁用库中的加载更多，设置列表的分隔线
     *
     * @param refreshWrapper      下拉刷新包裹类
     * @param pullRefreshAbleView 下拉刷新控件
     */
    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<P> refreshWrapper, P pullRefreshAbleView) {
    }

    /**
     * 设置列表控件需要的adapter。通常不用重写
     */
    @Override
    public ICommonListAdapter<BaseItemData> onListAdapterReady() {
        return new CommonListViewAdapter(getActivity(), getListDataSource(), (IScrollableListAdapterView) getScrollableView(), onListTypeClassesReady(), this, getRecyclerViewHelper(), onStickyTplViewTypeReady());
    }

    /**
     * 初始化滚动帮助类，这里基类基本就已经确定了滚动控件，所以直接在这里重写，如果日后谷歌再出其他控件，则继承BaseFastList系列界面类，复写该方法，按照步骤包裹对象和返回
     */
    @Override
    public ListScrollHelper<V> onInitScrollHelper() {
        return new ListScrollHelper<V>((IScrollableViewWrapper<V>) new ScrollableListViewWrapper((ScrollableListView) getScrollableView()));
    }

    /**
     * 滚动控件初始化完毕的回调，在这里可以添加一些滚动监听的操作，不过在列表帮助类里已经默认添加了，基本不需要
     */
    @Override
    public void onListScrollHelperReady(ListScrollHelper listScrollHelper) {
    }
}