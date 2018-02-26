package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.util.BaseLoadMoreViewFactory;
import oms.mmc.android.fast.framwork.util.IDataAdapter;
import oms.mmc.android.fast.framwork.util.IDataSource;
import oms.mmc.android.fast.framwork.util.ListAbleDelegateHelper;
import oms.mmc.android.fast.framwork.util.OnStateChangeListener;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.factory.load.base.BaseLoadViewFactory;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;

public abstract class BaseFastListActivity extends BaseFastActivity implements ListLayoutCallback<BaseItemData, BaseTpl.ViewHolder>, OnStateChangeListener<ArrayList<BaseItemData>>, BaseListAdapter.OnRecyclerViewItemClickListener, BaseListAdapter.OnRecyclerViewItemLongClickListener {
    private ListAbleDelegateHelper mDelegateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegateHelper = new ListAbleDelegateHelper(this);
        mDelegateHelper.startDelegate(getWindow().getDecorView());
        //初始化监听
        mDelegateHelper.getListAdapter().addOnItemClickListener(this);
        mDelegateHelper.getListAdapter().addOnItemLongClickListener(this);
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
    public void onListReady() {
        mDelegateHelper.setupRecyclerView();
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_base_fast_list, container);
    }

    @Override
    public IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> onListAdapterReady() {
        return new BaseListAdapter<BaseItemData>(getRecyclerView(), this, getListDataSource(), onListTypeClassesReady(), getRecyclerViewHelper(), onGetStickyTplViewType());
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

    public SwipeRefreshLayout getRefreshLayout() {
        return mDelegateHelper.getRefreshLayout();
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
        return mDelegateHelper.getListViewData();
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

    public void smoothMoveToTop() {
        mDelegateHelper.getScrollHelper().smoothMoveToTop();
    }

    public void moveToTop() {
        mDelegateHelper.getScrollHelper().moveToTop();
    }
}
