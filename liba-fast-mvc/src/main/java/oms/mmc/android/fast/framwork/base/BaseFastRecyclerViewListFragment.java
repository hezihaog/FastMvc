package oms.mmc.android.fast.framwork.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.util.ListAbleDelegateHelper;
import oms.mmc.android.fast.framwork.util.RecyclerViewListAbleDelegateHelper;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.adapter.CommonRecyclerViewAdapter;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterDataAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.RecyclerViewListConfigCallback;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeadersLinearLayoutManager;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.base.IScrollableViewWrapper;
import oms.mmc.helper.widget.ScrollableRecyclerView;
import oms.mmc.helper.wrapper.ScrollableRecyclerViewWrapper;

public abstract class BaseFastRecyclerViewListFragment<P extends IPullRefreshLayout, V extends ScrollableRecyclerView> extends BaseFastListFragment<P, V>
        implements RecyclerViewListConfigCallback {

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_base_fast_recycler_view_list, null);
    }

    @Override
    public ListAbleDelegateHelper<P, V> onInitListAbleDelegateHelper() {
        return new RecyclerViewListAbleDelegateHelper<P, V>(this, this, this);
    }

    @Override
    public RecyclerView.LayoutManager onGetListLayoutManager() {
        return new StickyHeadersLinearLayoutManager(getContext());
    }

    /**
     * 返回下拉刷新控件的包裹类，如果使用其他下拉刷新的控件，在这个回调上进行返回对应的包裹类，默认返回SwipeRefreshLayout的包裹类
     *
     * @param pullRefreshAbleView 布局中使用的下拉刷新控件
     */
    @Override
    public IPullRefreshWrapper<P> onInitPullRefreshWrapper(P pullRefreshAbleView) {
        return (IPullRefreshWrapper<P>) new SwipePullRefreshWrapper((SwipePullRefreshLayout) pullRefreshAbleView);
    }

    /**
     * 下拉刷新控件初始化完毕时回调，统一在这个回调中对下拉刷新控件进行相关设置
     */
    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<P> refreshWrapper, P pullRefreshAbleView) {
    }

    @Override
    public ICommonListAdapter<BaseItemData> onListAdapterReady() {
        CommonRecyclerViewAdapter adapter = new CommonRecyclerViewAdapter(getActivity(), getListDataSource()
                , getScrollableView(), onListTypeClassesReady(), this, getListHelper(), onStickyTplViewTypeReady());
        return new HeaderFooterDataAdapter<BaseItemData>(adapter);
    }

    @Override
    public void onListReadyAfter() {
    }

    @Override
    public ListScrollHelper<V> onInitScrollHelper() {
        return new ListScrollHelper<V>((IScrollableViewWrapper<V>) new ScrollableRecyclerViewWrapper((ScrollableRecyclerView) getScrollableView()));
    }

    @Override
    public void onListScrollHelperReady(ListScrollHelper listScrollHelper) {
    }
}
