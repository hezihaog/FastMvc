package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.ButterKnife;
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
    private SwipeRefreshLayout refreshLayout;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.fast_refresh_layout);
        refreshLayout.setId(MethodCompat.generateViewId());
        //初始化rv
        recyclerView = (RecyclerView) this.findViewById(R.id.base_list_view);
        recyclerView.setLayoutManager(getLayoutManager());
        if (listViewDataSource == null) {
            listViewDataSource = onListViewDataSourceReady();
        }
        if (listViewData == null) {
            listViewData = listViewDataSource.getOriginListViewData();
            originData = listViewDataSource.getOriginListViewData();
        }
        if (listViewAdapter == null) {
            listViewAdapter = (BaseListAdapter<T>) onListAdapterReady();
        }
        listViewAdapter.addOnItemClickListeners(this);
        listViewAdapter.addOnItemLongClickListener(this);
        if (recyclerViewHelper == null) {
            recyclerViewHelper = new RecyclerViewViewHelper<T>(refreshLayout, recyclerView);
            recyclerViewHelper.setAdapter(listViewAdapter);
        }
        recyclerViewHelper.init(onLoadViewFactoryReady());
        recyclerViewHelper.setDataSource(this.listViewDataSource);
        recyclerViewHelper.setOnStateChangeListener(this);
        listViewAdapter.setRecyclerViewHelper(recyclerViewHelper);
        ButterKnife.bind(this, this);
        onListViewReady();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recyclerViewHelper.destory();
    }


    @Override
    public void onListViewReady() {
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
        return new BaseListAdapter<T>(recyclerView, mActivity, listViewDataSource,
                onListViewTypeClassesReady(), recyclerViewHelper, BaseListAdapter.NOT_STICKY_SECTION);
    }

    public BaseListAdapter<T> getRecyclerViewAdapter() {
        return (BaseListAdapter<T>) recyclerView.getAdapter();
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new BaseLoadViewFactory();
    }

    @Override
    public void onStartRefresh(IDataAdapter<ArrayList<T>> listViewAdapter) {
        listViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<T>> listViewAdapter, ArrayList<T> result) {

    }

    @Override
    public void onStartLoadMore(IDataAdapter<ArrayList<T>> listViewAdapter) {

    }

    @Override
    public void onEndLoadMore(IDataAdapter<ArrayList<T>> listViewAdapter, ArrayList<T> result) {

    }

    @Override
    public void onItemClick(View view) {

    }

    @Override
    public boolean onItemLongClick(View view) {
        return false;
    }
}
