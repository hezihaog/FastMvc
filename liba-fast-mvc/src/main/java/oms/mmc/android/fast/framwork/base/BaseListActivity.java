package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.BaseMMCFastApplication;
import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.basiclib.util.MethodCompat;
import oms.mmc.android.fast.framwork.bean.IResult;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.PullToRefreshListView;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.BaseLoadViewFactory;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ILoadViewFactory;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ListViewHelper;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.OnStateChangeListener;

public abstract class BaseListActivity<T> extends BaseActivity implements ListLayoutCallback<T>, OnItemClickListener, OnStateChangeListener<ArrayList<T>>, OnItemLongClickListener {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pulltoRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView);
        pulltoRefreshListView.setId(MethodCompat.generateViewId());
//        listViewHelper = new ListViewHelper<T>(pulltoRefreshListView);
        listViewHelper.init(onLoadViewFactoryReady());
        listViewDataSource = onListViewDataSourceReady();
        listViewHelper.setDataSource(this.listViewDataSource);
        listView = pulltoRefreshListView.getRefreshableView();
        listView.setOnItemClickListener(this);
        listViewData = listViewDataSource.getOriginListViewData();
        originData = listViewDataSource.getOriginListViewData();
        listViewAdapter = (BaseListAdapter<T>) onListAdapterReady();
        listViewHelper.setAdapter(listViewAdapter);
        listViewAdapter.setOnItemClickListener(this);
        listViewAdapter.setOnItemLongClickListener(this);
        listViewHelper.setOnStateChangeListener(this);
        onListViewReady();
    }

    @Override
    public int onLayoutId() {
        return R.layout.base_list_activity;
    }

    @Override
    public void onListViewReady() {
        listViewHelper.refresh();
    }

    @Override
    public IDataAdapter<ArrayList<T>> onListAdapterReady() {
        return new BaseListAdapter<T>(listView, mActivity, listViewDataSource,
                onListViewTypeClassesReady(), listViewHelper, 0);
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new BaseLoadViewFactory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listViewHelper.destory();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public void onStartRefresh(IDataAdapter<ArrayList<T>> adapter) {

    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<T>> adapter, ArrayList<T> result) {

    }

    @Override
    public void onStartLoadMore(IDataAdapter<ArrayList<T>> adapter) {

    }

    @Override
    public void onEndLoadMore(IDataAdapter<ArrayList<T>> adapter, ArrayList<T> result) {

    }

    @Override
    public void onApiStart(String tag) {
    }

    @Override
    public void onApiLoading(long count, long current, String tag) {
    }

    @Override
    public void onApiSuccess(IResult res, String tag) {
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        BaseMMCFastApplication.showToast("网络请求错误");
        t.printStackTrace();
        onApiError(tag);
    }

    @Override
    public void onParseError(String tag) {
        BaseMMCFastApplication.showToast("数据解析错误");
        onApiError(tag);
    }

    @Override
    protected void onApiError(String tag) {
    }
}
