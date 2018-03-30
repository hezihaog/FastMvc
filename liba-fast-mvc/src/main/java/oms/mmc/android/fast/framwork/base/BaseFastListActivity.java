package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.loadview.BaseLoadMoreViewFactory;
import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.util.IListAbleDelegateHelper;
import oms.mmc.android.fast.framwork.util.ListAbleDelegateHelper;
import oms.mmc.android.fast.framwork.util.ListHelper;
import oms.mmc.android.fast.framwork.util.OnLoadStateChangeListener;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.list.helper.IAssistHelper;
import oms.mmc.android.fast.framwork.widget.list.sticky.ItemStickyDelegate;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.IListConfigCallback;
import oms.mmc.factory.load.base.BaseLoadViewFactory;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.base.IScrollableAdapterView;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: BaseFastListActivity
 * Date: on 2018/3/28  下午6:51
 * Auther: zihe
 * Descirbe:列表基类，没有引入控件，只有有具体控件后继承本类，例如rv、lv就直接继承{@link BaseFastRecyclerViewListActivity} {@link BaseFastListViewActivity}
 * Email: hezihao@linghit.com
 */

public abstract class BaseFastListActivity<P extends IPullRefreshLayout, V extends IScrollableAdapterView> extends BaseFastActivity
        implements ListLayoutCallback<BaseItemData, V>, OnLoadStateChangeListener<BaseItemData>, IListAbleDelegateHelperHost<P, V>,
        ICommonListAdapter.OnScrollableViewItemClickListener, IListConfigCallback,
        ICommonListAdapter.OnScrollableViewItemLongClickListener, IPullRefreshUi<P> {
    private ListAbleDelegateHelper<P, V> mDelegateHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegateHelper = onInitListAbleDelegateHelper();
        mDelegateHelper.startDelegate(getActivity(), getWindow().getDecorView());
        //初始化监听
        ICommonListAdapter adapter = mDelegateHelper.getListAdapter();
        adapter.addOnItemClickListener(this);
        adapter.addOnItemLongClickListener(this);
        mDelegateHelper.getListHelper().setOnLoadStateChangeListener(this);
        onListReady();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDelegateHelper.destroyListHelper();
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
        return ItemStickyDelegate.NOT_STICKY_SECTION;
    }

    @Override
    public IListAbleDelegateHelper getListAbleDelegateHelper() {
        return mDelegateHelper;
    }

    public IPullRefreshWrapper<?> getRefreshWrapper() {
        return mDelegateHelper.getRefreshWrapper();
    }

    public IPullRefreshLayout getRefreshLayout() {
        return mDelegateHelper.getRefreshWrapper().getPullRefreshAbleView();
    }

    public V getScrollableView() {
        return mDelegateHelper.getScrollableView();
    }

    public ListHelper<BaseItemData> getListHelper() {
        return mDelegateHelper.getListHelper();
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