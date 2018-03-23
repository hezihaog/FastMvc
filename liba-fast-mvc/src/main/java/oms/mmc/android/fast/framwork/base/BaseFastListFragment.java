package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeadersLinearLayoutManager;
import oms.mmc.factory.load.base.BaseLoadViewFactory;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.widget.ScrollableRecyclerView;
import oms.mmc.helper.wrapper.ScrollableRecyclerViewWrapper;

public abstract class BaseFastListFragment<P extends IPullRefreshLayout> extends BaseFastFragment
        implements ListLayoutCallback<BaseItemData, BaseTpl.ViewHolder>,
        OnStateChangeListener<ArrayList<BaseItemData>>, BaseListAdapter.OnRecyclerViewItemClickListener
        , BaseListAdapter.OnRecyclerViewItemLongClickListener, IPullRefreshUi<P> {
    private ListAbleDelegateHelper<P> mDelegateHelper;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_base_fast_list, null);
    }

    @Override
    public View onLazyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = super.onLazyCreateView(inflater, container, savedInstanceState);
        mDelegateHelper = new ListAbleDelegateHelper<P>(this, this);
        mDelegateHelper.startDelegate(rootLayout);
        //初始化监听
        mDelegateHelper.getListAdapter().addOnItemClickListener(this);
        mDelegateHelper.getListAdapter().addOnItemLongClickListener(this);
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

    @Override
    protected void onLazyViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onLazyViewCreated(view, savedInstanceState);
        mDelegateHelper.setupScrollHelper();
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
        mDelegateHelper.setupRecyclerView();
    }

    @Override
    public IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> onListAdapterReady() {
        return new BaseListAdapter<BaseItemData>(getRecyclerView(), getActivity(), getListDataSource()
                , onListTypeClassesReady(), getRecyclerViewHelper(), onGetStickyTplViewType(), this);
    }

    @Override
    public ListScrollHelper onGetScrollHelper() {
        //默认都是rv，这里默认使用rv的，如果是使用其他的，复写该方法，返回对应的包裹类和控件
        return new ListScrollHelper(new ScrollableRecyclerViewWrapper((ScrollableRecyclerView) getRecyclerView()));
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
    public void onStartRefresh(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, boolean isFirst, boolean isReverse) {
    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
    }

    @Override
    public void onStartLoadMore(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, boolean isFirst, boolean isReverse) {
    }

    @Override
    public void onEndLoadMore(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
    }

    @Override
    public void onItemClick(View view, BaseTpl clickTpl, int position) {
    }

    @Override
    public boolean onItemLongClick(View view, BaseTpl longClickTpl, int position) {
        return false;
    }

    @Override
    public int onGetStickyTplViewType() {
        return BaseListAdapter.NOT_STICKY_SECTION;
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

    public RecyclerView getRecyclerView() {
        return mDelegateHelper.getRecyclerView();
    }

    public RecyclerViewViewHelper<BaseItemData> getRecyclerViewHelper() {
        return mDelegateHelper.getRecyclerViewHelper();
    }

    public IDataSource<BaseItemData> getListDataSource() {
        return mDelegateHelper.getListDataSource();
    }

    public ArrayList<BaseItemData> getListViewData() {
        return mDelegateHelper.getListData();
    }

    public ArrayList<BaseItemData> getOriginData() {
        return mDelegateHelper.getOriginData();
    }

    public BaseListAdapter<BaseItemData> getListAdapter() {
        return mDelegateHelper.getListAdapter();
    }

    public HeaderFooterAdapter getRecyclerViewAdapter() {
        return (HeaderFooterAdapter) mDelegateHelper.getRecyclerView().getAdapter();
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
