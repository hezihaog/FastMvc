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

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: BaseFastListViewActivity
 * Date: on 2018/3/28  下午6:55
 * Auther: zihe
 * Descirbe:RecyclerView控件使用的Activity
 * Email: hezihao@linghit.com
 */
public abstract class BaseFastRecyclerViewListActivity
        <P extends IPullRefreshLayout, V extends ScrollableRecyclerView> extends BaseFastListActivity<P, V> implements RecyclerViewListConfigCallback {

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_base_fast_recycler_view_list, container);
    }

    @Override
    public ListAbleDelegateHelper<P, V> onInitListAbleDelegateHelper() {
        return new RecyclerViewListAbleDelegateHelper<P, V>(this, this, this);
    }

    @Override
    public RecyclerView.LayoutManager onGetListLayoutManager() {
        return new StickyHeadersLinearLayoutManager(getActivity());
    }

    @Override
    public ICommonListAdapter<BaseItemData> onListAdapterReady() {
        CommonRecyclerViewAdapter adapter = new CommonRecyclerViewAdapter(this, getListDataSource(),
                getScrollableView(), onListTypeClassesReady(), this, getListHelper(), onStickyTplViewTypeReady());
        return new HeaderFooterDataAdapter<BaseItemData>(adapter);
    }

    @Override
    public void onListReadyAfter() {
    }

    @Override
    public void onListScrollHelperReady(ListScrollHelper<V> listScrollHelper) {
    }

    @Override
    public IPullRefreshWrapper<P> onInitPullRefreshWrapper(P pullRefreshAbleView) {
        return (IPullRefreshWrapper<P>) new SwipePullRefreshWrapper((SwipePullRefreshLayout) pullRefreshAbleView);
    }

    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<P> refreshWrapper, P pullRefreshAbleView) {
    }

    @Override
    public ListScrollHelper<V> onInitScrollHelper() {
        //默认都是rv，这里默认使用rv的，如果是使用其他的，复写该方法，返回对应的包裹类和控件
        return new ListScrollHelper<V>((IScrollableViewWrapper<V>) new ScrollableRecyclerViewWrapper(getScrollableView()));
    }
}