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
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterAdapter;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterDataAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeadersLinearLayoutManager;
import oms.mmc.factory.load.base.BaseLoadViewFactory;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.widget.ScrollableRecyclerView;
import oms.mmc.helper.wrapper.ScrollableRecyclerViewWrapper;

public abstract class BaseFastListActivity<P extends IPullRefreshLayout> extends BaseFastActivity
        implements ListLayoutCallback<BaseItemData>, OnStateChangeListener<BaseItemData>,
        BaseListAdapter.OnRecyclerViewItemClickListener,
        BaseListAdapter.OnRecyclerViewItemLongClickListener, IPullRefreshUi<P> {
    private ListAbleDelegateHelper<P> mDelegateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegateHelper = new ListAbleDelegateHelper<P>(this, this);
        mDelegateHelper.startDelegate(getWindow().getDecorView());
        //初始化监听
        HeaderFooterDataAdapter headerFooterAdapter = (HeaderFooterDataAdapter) mDelegateHelper.getListAdapter();
        ((BaseListAdapter)headerFooterAdapter.getAdapter()).addOnItemClickListener(this);
        ((BaseListAdapter)headerFooterAdapter.getAdapter()).addOnItemLongClickListener(this);
        mDelegateHelper.getRecyclerViewHelper().setOnStateChangeListener(this);
        onListReady();
        mDelegateHelper.setupScrollHelper();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDelegateHelper.destroyRecyclerViewHelper();
    }

    @Override
    public RecyclerView.LayoutManager onGetListLayoutManager() {
        return new StickyHeadersLinearLayoutManager(getActivity());
    }

    @Override
    public void onListReady() {
        mDelegateHelper.setupRecyclerView();
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_base_fast_list, container);
    }

    @Override
    public IDataAdapter<BaseItemData> onListAdapterReady() {
        BaseListAdapter adapter =  new BaseListAdapter<BaseItemData>(getRecyclerView(), getActivity(), getListDataSource()
                , onListTypeClassesReady(), getRecyclerViewHelper(), onGetStickyTplViewType(), this);
        return new HeaderFooterDataAdapter(adapter);
    }

    @Override
    public ListScrollHelper onGetScrollHelper() {
        //默认都是rv，这里默认使用rv的，如果是使用其他的，复写该方法，返回对应的包裹类和控件
        return new ListScrollHelper(new ScrollableRecyclerViewWrapper((ScrollableRecyclerView) getRecyclerView()));
    }

    @Override
    public IPullRefreshWrapper<P> onInitPullRefreshWrapper(P pullRefreshAbleView) {
        return (IPullRefreshWrapper<P>) new SwipePullRefreshWrapper((SwipePullRefreshLayout) pullRefreshAbleView);
    }

    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<P> refreshWrapper, P pullRefreshAbleView) {
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
    public void onStartRefresh(IDataAdapter<BaseItemData> adapter, boolean isFirst, boolean isReverse) {
    }

    @Override
    public void onEndRefresh(IDataAdapter<BaseItemData> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
    }

    @Override
    public void onStartLoadMore(IDataAdapter<BaseItemData> adapter, boolean isFirst, boolean isReverse) {
    }

    @Override
    public void onEndLoadMore(IDataAdapter<BaseItemData> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
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

    public ArrayList<BaseItemData> getListData() {
        return mDelegateHelper.getListData();
    }

    public ArrayList<BaseItemData> getOriginData() {
        return mDelegateHelper.getOriginData();
    }

    public BaseListAdapter<BaseItemData> getListAdapter() {
        return (BaseListAdapter<BaseItemData>) mDelegateHelper.getListAdapter();
    }

    public HeaderFooterAdapter getRecyclerViewAdapter() {
        return (HeaderFooterAdapter) mDelegateHelper.getRecyclerView().getAdapter();
    }

    public ILoadViewFactory getLoadViewFactory() {
        return mDelegateHelper.getLoadViewFactory();
    }

    public ListScrollHelper getScrollHelper() {
        return mDelegateHelper.getScrollHelper();
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
