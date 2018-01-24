package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.basiclib.util.MethodCompat;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.PullToRefreshListView;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.BaseLoadViewFactory;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ILoadViewFactory;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ListViewHelper;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.OnStateChangeListener;

public abstract class BaseListFragment<T> extends BaseFragment implements ListLayoutCallback<T>, OnItemClickListener, OnStateChangeListener<ArrayList<T>>, OnItemLongClickListener {
    /**
     * 下来刷新控件
     */
    protected PullToRefreshListView pulltoRefreshListView;
    /**
     * 列表
     */
    protected ListView listView;
    /**
     * 列表加载帮助类
     */
    protected ListViewHelper<T> listViewHelper;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        pulltoRefreshListView = (PullToRefreshListView) root.findViewById(R.id.pullToRefreshListView);
        pulltoRefreshListView.setId(MethodCompat.generateViewId());
        if (listViewHelper == null) {
            listViewHelper = new ListViewHelper<T>(pulltoRefreshListView);
            listViewHelper.init(onLoadViewFactoryReady());
        }
        if (listViewDataSource == null) {
            listViewDataSource = onListViewDataSourceReady();
        }
        listViewHelper.setDataSource(this.listViewDataSource);
        listView = pulltoRefreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.drawable.base_divider_line));
        listView.setOnItemClickListener(this);
        if (listViewData == null) {
            listViewData = listViewDataSource.getOriginListViewData();
            originData = listViewDataSource.getOriginListViewData();
        }
        listViewHelper.setOnStateChangeListener(this);
        if (listViewAdapter == null) {
            listViewAdapter = (BaseListAdapter<T>) onListAdapterReady();
            listViewHelper.setAdapter(listViewAdapter);
        }
        listViewAdapter.setOnItemClickListener(this);
        listViewAdapter.setOnItemLongClickListener(this);
        ButterKnife.bind(this, root);
        onListViewReady();
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listViewHelper.destory();
    }


    @Override
    public void onListViewReady() {
        if (listViewData.size() == 0) {
            listViewHelper.refresh();
        }
    }

    @Override
    public int onLayoutId() {
        return R.layout.base_list_frag;
    }

    @Override
    public IDataAdapter<ArrayList<T>> onListAdapterReady() {
        return new BaseListAdapter<T>(listView, mActivity, listViewDataSource, onListViewTypeClassesReady(), listViewHelper);
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new BaseLoadViewFactory();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public void onStartRefresh(IDataAdapter<ArrayList<T>> listViewAdapter) {

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
}
