package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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
import oms.mmc.helper.base.IScrollableAdapterView;
import oms.mmc.helper.base.IScrollableView;
import oms.mmc.helper.widget.ScrollableRecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: BaseFastListFragment
 * Date: on 2018/3/28  下午11:18
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class BaseFastListFragment<P extends IPullRefreshLayout, V extends IScrollableAdapterView> extends BaseFastFragment
        implements ListLayoutCallback<BaseItemData, V>, IListAbleDelegateHelperHost<P, V>,
        OnStateChangeListener<BaseItemData>, ICommonListAdapter.OnScrollableViewItemClickListener,
        IListConfigCallback, ICommonListAdapter.OnScrollableViewItemLongClickListener, IPullRefreshUi<P> {

    private ListAbleDelegateHelper<P, V> mDelegateHelper;

    @Override
    public View onLazyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = super.onLazyCreateView(inflater, container, savedInstanceState);
        mDelegateHelper = onInitListAbleDelegateHelper();
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
            mDelegateHelper.destroyListHelper();
        }
    }

    @Override
    public void onListReady() {
        mDelegateHelper.setupListWidget();
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