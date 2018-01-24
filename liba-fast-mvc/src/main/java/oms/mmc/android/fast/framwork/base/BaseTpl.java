package oms.mmc.android.fast.framwork.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.ButterKnife;
import oms.mmc.android.fast.framwork.BaseMMCFastApplication;
import oms.mmc.android.fast.framwork.bean.IResult;
import oms.mmc.android.fast.framwork.bean.TplBase;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ListViewHelper;

/**
 * 列表条目基础模板
 */
public abstract class BaseTpl<T> implements ApiCallback, LayoutCallback, Serializable, View.OnAttachStateChangeListener {
    protected BaseMMCFastApplication ac;
    protected BaseActivity _activity;
    protected Intent _intent;
    protected Bundle _Bundle;

    protected ListViewHelper listViewHelper;
    protected BaseListAdapter<? extends TplBase> listViewAdapter;
    protected IDataSource<? extends TplBase> listViewDataSource;
    protected ArrayList<? extends TplBase> listViewData;
    protected AbsListView listView;
    protected int itemViewType = -1;
    protected View root;
    protected int position;
    protected T bean;

    public void config(BaseListAdapter<? extends TplBase> adapter, ArrayList<? extends TplBase> data, IDataSource<? extends TplBase> dataSource, AbsListView absListView, ListViewHelper listViewHelper) {
        this.listViewAdapter = adapter;
        this.listViewDataSource = dataSource;
        this.listViewData = data;
        this.listView = absListView;
        this.listViewHelper = listViewHelper;
    }

    public void init(Context context, int itemViewType) {
        this.itemViewType = itemViewType;
        this._activity = (BaseActivity) context;
        this._intent = _activity.getIntent();
        if (this._intent != null) {
            this._Bundle = _intent.getExtras();
        }
        ac = (BaseMMCFastApplication) context.getApplicationContext();
        initView();
    }

    protected void initView() {
        onLayoutBefore();
        root = View.inflate(_activity, onLayoutId(), null);
        root.addOnAttachStateChangeListener(this);
        ButterKnife.bind(this, root);
        onLayoutAfter();
    }

    public View getRoot() {
        return root;
    }

    @Override
    public void onLayoutBefore() {

    }

    @Override
    public abstract int onLayoutId();

    @Override
    public void onLayoutAfter() {

    }

    protected void onItemClick() {

    }

    protected void onItemLongClick() {
    }

    public void setBeanPosition(ArrayList<? extends TplBase> listViewData, T item, int position) {
        this.listViewData = listViewData;
        this.bean = item;
        this.position = position;
    }

    public abstract void render();

    public String intentStr(String key) {
        return _intent.getStringExtra(key);
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

    protected void onApiError(String tag) {

    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {

    }
}
