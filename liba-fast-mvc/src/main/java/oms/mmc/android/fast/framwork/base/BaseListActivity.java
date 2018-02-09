package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.basiclib.util.MethodCompat;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.BaseLoadViewFactory;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ILoadViewFactory;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.OnStateChangeListener;

public abstract class BaseListActivity<T> extends BaseActivity implements ListLayoutCallback<T>, OnStateChangeListener<ArrayList<T>>, BaseListAdapter.OnRecyclerViewItemClickListener, BaseListAdapter.OnRecyclerViewItemLongClickListener {
    /**
     * 下来刷新控件
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
    protected IDataSource<T> listDataSource;
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
    protected BaseListAdapter<T> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.fast_refresh_layout);
        refreshLayout.setId(MethodCompat.generateViewId());
        //初始化rv
        recyclerView = (RecyclerView) this.findViewById(R.id.base_list_view);
        recyclerView.setLayoutManager(getListLayoutManager());
        if (listDataSource == null) {
            listDataSource = onListDataSourceReady();
        }
        if (listViewData == null) {
            listViewData = listDataSource.getOriginListViewData();
            originData = listDataSource.getOriginListViewData();
        }
        if (listAdapter == null) {
            listAdapter = (BaseListAdapter<T>) onListAdapterReady();
        }
        listAdapter.addOnItemClickListener(this);
        listAdapter.addOnItemLongClickListener(this);
        if (recyclerViewHelper == null) {
            recyclerViewHelper = new RecyclerViewViewHelper<T>(refreshLayout, recyclerView);
            recyclerViewHelper.setAdapter(listAdapter);
        }
        recyclerViewHelper.init(onLoadViewFactoryReady());
        recyclerViewHelper.setDataSource(this.listDataSource);
        recyclerViewHelper.setOnStateChangeListener(this);
        listAdapter.setRecyclerViewHelper(recyclerViewHelper);
        //加入滚动监听
        ListScrollHelper scrollHelper = onGetScrollHelper();
        recyclerViewHelper.setupScrollHelper(scrollHelper);
        onListReady();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recyclerViewHelper.destroy();
    }

    @Override
    public void onListReady() {
        //rv在25版本加入了预缓冲，粘性头部在该功能上不兼容，用此开关关闭该功能
        recyclerView.getLayoutManager().setItemPrefetchEnabled(false);
        if (listViewData.size() == 0) {
            recyclerViewHelper.refresh();
        }
    }

    @Override
    public int onLayoutId() {
        return R.layout.base_list_activity;
    }

    @Override
    public IDataAdapter<ArrayList<T>> onListAdapterReady() {
        return new BaseListAdapter<T>(recyclerView, getActivity(), listDataSource,
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
