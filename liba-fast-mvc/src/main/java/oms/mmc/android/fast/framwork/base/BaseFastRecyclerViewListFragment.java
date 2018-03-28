package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.loadview.BaseLoadMoreViewFactory;
import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.util.ListAbleDelegateHelper;
import oms.mmc.android.fast.framwork.util.OnStateChangeListener;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.list.helper.IAssistHelper;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.adapter.CommonRecyclerViewAdapter;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterAdapter;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterDataAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.RecyclerViewListConfigCallback;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeadersLinearLayoutManager;
import oms.mmc.factory.load.base.BaseLoadViewFactory;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.base.IScrollableAdapterView;
import oms.mmc.helper.base.IScrollableView;
import oms.mmc.helper.base.IScrollableViewWrapper;
import oms.mmc.helper.widget.ScrollableRecyclerView;
import oms.mmc.helper.wrapper.ScrollableRecyclerViewWrapper;

public abstract class BaseFastRecyclerViewListFragment<P extends IPullRefreshLayout, V extends IScrollableAdapterView> extends BaseFastFragment
        implements ListLayoutCallback<BaseItemData, V>,
        OnStateChangeListener<BaseItemData>, ICommonListAdapter.OnScrollableViewItemClickListener,
        RecyclerViewListConfigCallback, ICommonListAdapter.OnScrollableViewItemLongClickListener, IPullRefreshUi<P> {
    private ListAbleDelegateHelper<P, V> mDelegateHelper;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_base_fast_list, null);
    }

    @Override
    public View onLazyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = super.onLazyCreateView(inflater, container, savedInstanceState);
        mDelegateHelper = new ListAbleDelegateHelper<P, V>(this, this, this);
        mDelegateHelper.startDelegate(getActivity(), rootLayout);
        //初始化监听
        ICommonListAdapter adapter = mDelegateHelper.getListAdapter();
        adapter.addOnItemClickListener(this);
        adapter.addOnItemLongClickListener(this);
        mDelegateHelper.getRecyclerViewHelper().setOnStateChangeListener(this);
        mDelegateHelper.notifyListReady();
        return rootLayout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDelegateHelper != null) {
            mDelegateHelper.destroyRecyclerViewHelper();
        }
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
    public RecyclerView.LayoutManager onGetListLayoutManager() {
        return new StickyHeadersLinearLayoutManager(getContext());
    }

    @Override
    public void onListReady() {
        mDelegateHelper.setupListWidget();
    }

    @Override
    public void onListReadyAfter() {
    }

    @Override
    public ICommonListAdapter<BaseItemData> onListAdapterReady() {
        CommonRecyclerViewAdapter adapter = new CommonRecyclerViewAdapter((ScrollableRecyclerView) getScrollableView()
                , getActivity(), getListDataSource(), onListTypeClassesReady(), getRecyclerViewHelper(), this, onStickyTplViewTypeReady());
        return new HeaderFooterDataAdapter<BaseItemData>(adapter);
    }

    @Override
    public ListScrollHelper<V> onInitScrollHelper() {
        return new ListScrollHelper<V>((IScrollableViewWrapper<V>) new ScrollableRecyclerViewWrapper((ScrollableRecyclerView) getScrollableView()));
    }

    @Override
    public void onListScrollHelperReady(ListScrollHelper listScrollHelper) {
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new BaseLoadViewFactory();
    }

    @Override
    public ILoadMoreViewFactory onLoadMoreViewFactoryReady() {
        return new BaseLoadMoreViewFactory();
    }

    @Override
    public void onStartRefresh(ICommonListAdapter<BaseItemData> adapter, boolean isFirst, boolean isReverse) {
    }

    @Override
    public void onEndRefresh(ICommonListAdapter<BaseItemData> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
    }

    @Override
    public void onStartLoadMore(ICommonListAdapter<BaseItemData> adapter, boolean isFirst, boolean isReverse) {
    }

    @Override
    public void onEndLoadMore(ICommonListAdapter<BaseItemData> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
    }

    @Override
    public void onItemClick(View view, BaseTpl clickTpl, int position) {
    }

    @Override
    public boolean onItemLongClick(View view, BaseTpl longClickTpl, int position) {
        return false;
    }

    @Override
    public int onStickyTplViewTypeReady() {
        return CommonRecyclerViewAdapter.NOT_STICKY_SECTION;
    }

    public ListAbleDelegateHelper getListAbleDelegateHelper() {
        return mDelegateHelper;
    }

    public IPullRefreshWrapper<?> getRefreshLayoutWrapper() {
        return mDelegateHelper.getRefreshWrapper();
    }

    public IPullRefreshLayout getRefreshLayout() {
        return mDelegateHelper.getRefreshWrapper().getPullRefreshAbleView();
    }

    public IScrollableView getScrollableView() {
        return mDelegateHelper.getScrollableView();
    }

    public RecyclerViewViewHelper<BaseItemData> getRecyclerViewHelper() {
        return mDelegateHelper.getRecyclerViewHelper();
    }

    public IPullRefreshWrapper<?> getRefreshWrapper() {
        return mDelegateHelper.getRefreshWrapper();
    }

    public IDataSource<BaseItemData> getListDataSource() {
        return mDelegateHelper.getListDataSource();
    }

    public ArrayList<BaseItemData> getListData() {
        return mDelegateHelper.getListData();
    }

    public ICommonListAdapter<BaseItemData> getListAdapter() {
        return mDelegateHelper.getListAdapter();
    }

    public IAssistHelper getAssistHelper() {
        return mDelegateHelper.getAssistHelper();
    }

    public HeaderFooterAdapter getRecyclerViewAdapter() {
        ScrollableRecyclerView scrollableRecyclerView = (ScrollableRecyclerView) mDelegateHelper.getScrollableView();
        return (HeaderFooterAdapter) scrollableRecyclerView.getAdapter();
    }

    public ILoadViewFactory getLoadViewFactory() {
        return mDelegateHelper.getLoadViewFactory();
    }

    /**
     * 缓缓回到顶部
     *
     * @param isReverse 是否是反转布局
     */
    public void smoothMoveToTop(boolean isReverse) {
        mDelegateHelper.smoothMoveToTop(isReverse);
    }

    /**
     * 瞬时回到顶部
     *
     * @param isReverse 是否是反转布局
     */
    public void moveToTop(boolean isReverse) {
        mDelegateHelper.moveToTop(isReverse);
    }
}
