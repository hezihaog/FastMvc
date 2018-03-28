package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
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
import oms.mmc.android.fast.framwork.widget.rv.adapter.CommonRecyclerViewAdapter;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.IListConfigCallback;
import oms.mmc.factory.load.base.BaseLoadViewFactory;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.base.IScrollableAdapterView;
import oms.mmc.helper.widget.ScrollableRecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: BaseFastListActivity
 * Date: on 2018/3/28  下午6:51
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class BaseFastListActivity<P extends IPullRefreshLayout, V extends IScrollableAdapterView> extends BaseFastActivity
        implements ListLayoutCallback<BaseItemData, V>, OnStateChangeListener<BaseItemData>,
        ICommonListAdapter.OnScrollableViewItemClickListener, IListConfigCallback,
        ICommonListAdapter.OnScrollableViewItemLongClickListener, IPullRefreshUi<P> {
    private ListAbleDelegateHelper<P, V> mDelegateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegateHelper = new ListAbleDelegateHelper<P, V>(this, this, this);
        mDelegateHelper.startDelegate(getActivity(), getWindow().getDecorView());
        //初始化监听
        ICommonListAdapter adapter = mDelegateHelper.getListAdapter();
        adapter.addOnItemClickListener(this);
        adapter.addOnItemLongClickListener(this);
        mDelegateHelper.getRecyclerViewHelper().setOnStateChangeListener(this);
        onListReady();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDelegateHelper.destroyRecyclerViewHelper();
    }

    @Override
    public void onListReady() {
        mDelegateHelper.setupListWidget();
    }

    @Override
    public void onListReadyAfter() {
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_base_fast_recycler_view_list, container);
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

    public V getScrollableView() {
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
        return (HeaderFooterAdapter) ((ScrollableRecyclerView) mDelegateHelper.getScrollableView()).getAdapter();
    }

    public ILoadViewFactory getLoadViewFactory() {
        return mDelegateHelper.getLoadViewFactory();
    }

    public ListScrollHelper<V> getScrollHelper() {
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