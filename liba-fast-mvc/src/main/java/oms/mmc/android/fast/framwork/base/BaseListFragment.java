package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.basiclib.util.MethodCompat;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.BaseLoadViewFactory;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ILoadViewFactory;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.OnStateChangeListener;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.RecyclerViewViewHelper;

public abstract class BaseListFragment<T> extends BaseFragment implements ListLayoutCallback<T>, OnStateChangeListener<ArrayList<T>>, BaseListAdapter.OnRecyclerViewItemClickListener, BaseListAdapter.OnRecyclerViewItemLongClickListener {
    /**
     * 下拉刷新控件
     */
    protected SwipeRefreshLayout refreshLayout;
    /**
     * 列表
     */
    protected RecyclerView recyclerView;
    /**
     * 列表加载帮助类
     */
    protected RecyclerViewViewHelper<T> recyclerViewHelper;
    /**
     * 列表数据源
     */
    protected IDataSource<T> listViewDataSource;
    /**
     * 列表数据
     */
    protected ArrayList<T> listViewData;
    /**
     * 原始数据
     */
    protected ArrayList<T> originData;
    /**
     * 列表适配器
     */
    protected BaseListAdapter<T> listViewAdapter;

    @Override
    public View onLazyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onLazyCreateView(inflater, container, savedInstanceState);
        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.fast_refresh_layout);
        refreshLayout.setId(MethodCompat.generateViewId());
        //初始化rv
        recyclerView = (RecyclerView) root.findViewById(R.id.base_list_view);
        recyclerView.setLayoutManager(getListLayoutManager());
        if (listViewDataSource == null) {
            listViewDataSource = onListDataSourceReady();
        }
        if (listViewData == null) {
            listViewData = listViewDataSource.getOriginListViewData();
            originData = listViewDataSource.getOriginListViewData();
        }
        if (listViewAdapter == null) {
            listViewAdapter = (BaseListAdapter<T>) onListAdapterReady();
        }
        listViewAdapter.addOnItemClickListener(this);
        listViewAdapter.addOnItemLongClickListener(this);
        if (recyclerViewHelper == null) {
            recyclerViewHelper = new RecyclerViewViewHelper<T>(refreshLayout, recyclerView);
            recyclerViewHelper.setAdapter(listViewAdapter);
        }
        recyclerViewHelper.init(onLoadViewFactoryReady());
        recyclerViewHelper.setDataSource(this.listViewDataSource);
        recyclerViewHelper.setOnStateChangeListener(this);
        listViewAdapter.setRecyclerViewHelper(recyclerViewHelper);
        onListReady();
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recyclerViewHelper != null) {
            recyclerViewHelper.destroy();
        }
    }


    @Override
    public void onListReady() {
        //rv在25版本加入了预缓冲，粘性头部在该功能上不兼容，用此开关关闭该功能
        recyclerView.getLayoutManager().setItemPrefetchEnabled(false);
        if (listViewData.size() == 0) {
            //一开始先加一个尾部加载更多条目
            listViewAdapter.addLoaderMoreFooterItem();
            recyclerViewHelper.refresh();
        }
    }

    @Override
    public int onLayoutId() {
        return R.layout.base_list_frag;
    }

    @Override
    public IDataAdapter<ArrayList<T>> onListAdapterReady() {
        return new BaseListAdapter<T>(recyclerView, mActivity, listViewDataSource,
                onListTypeClassesReady(), recyclerViewHelper, onGetStickyTplViewType());
    }

    public BaseListAdapter<T> getRecyclerViewAdapter() {
        return (BaseListAdapter<T>) recyclerView.getAdapter();
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new BaseLoadViewFactory();
    }

    @Override
    public void onStartRefresh(IDataAdapter<ArrayList<T>> adapter, boolean isFirst) {

    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<T>> adapter, ArrayList<T> result, boolean isFirst) {

    }

    @Override
    public void onStartLoadMore(IDataAdapter<ArrayList<T>> adapter, boolean isFirst) {

    }

    @Override
    public void onEndLoadMore(IDataAdapter<ArrayList<T>> adapter, ArrayList<T> result, boolean isFirst) {

    }

    @Override
    public void onItemClick(View view) {

    }

    @Override
    public boolean onItemLongClick(View view) {
        return false;
    }

    @Override
    public int onGetStickyTplViewType() {
        return BaseListAdapter.NOT_STICKY_SECTION;
    }
}
